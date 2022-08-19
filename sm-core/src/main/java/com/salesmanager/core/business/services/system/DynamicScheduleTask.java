package com.salesmanager.core.business.services.system;

import com.salesmanager.core.business.repositories.system.SystemTimerTaskRepository;
import com.salesmanager.core.business.services.system.task.TimerTaskMethod;
import com.salesmanager.core.model.system.SystemTimerTask;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.LinkedList;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {


    @Autowired      //注入mapper
    private SystemTimerTaskRepository timerTaskRepository;
    private MethodHandles.Lookup lookup = MethodHandles.lookup();

    /**
     * 执行定时任务.
     */
    @TimerTaskMethod
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<SystemTimerTask> tasks = timerTaskRepository.findAllByAvailableIsTrue();
        if (tasks != null && tasks.size() > 0) {

            for (SystemTimerTask task : tasks) {
                taskRegistrar.addTriggerTask(
                        //1.添加任务内容(Runnable)
                        () -> {
                            String className = task.getTaskFullyQulifiedClassName();
                            String methodName = task.getTaskMethod();
                            String fields = task.getTaskOrderedParameterFields();
                            String parameters = task.getTaskOrderedParameters();
                            runTaskAsync(className, methodName, fields, parameters);
                        },
                        //2.设置执行周期(Trigger)
                        triggerContext -> {
                            //2.1 从数据库获取执行周期
                            //2.2 合法性校验.
                            //2.3 返回执行周期(Date)
                            return new CronTrigger(task.getCron()).nextExecutionTime(triggerContext);
                        }
                );
            }

        }

    }

    @Async
    void runTaskAsync(String className, String methodName, String fields, String parameters) {
        try {

            if (fields != null && parameters != null) {
                JSONArray fieldsF = new JSONArray(fields);
                JSONArray parametersV = new JSONArray(parameters);
                if (fieldsF.length() == parametersV.length()) {
                    List<Class<?>> clazz = new LinkedList<Class<?>>();
                    for (int i = 0; i < fieldsF.length(); i++) {
                        clazz.add(Class.forName(fieldsF.getString(i)));
                    }

                    Object[] pf = new Object[fieldsF.length()];
                    for (int i = 0; i < parametersV.length(); i++) {
                        pf[i] = (parametersV.get(i));
                    }

                    MethodHandle valueOfMethodHandler = lookup.findVirtual(Class.forName(className), methodName, MethodType.methodType(void.class, clazz));
                    //执行方法句柄
                    valueOfMethodHandler.invokeExact(pf);
                }
            } else {
                MethodHandle valueOfMethodHandler = lookup.findVirtual(Class.forName(className), methodName, MethodType.methodType(void.class));
                //执行方法句柄
                valueOfMethodHandler.invokeExact();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}