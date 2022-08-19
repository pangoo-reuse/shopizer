package com.salesmanager.core.business.services.promotion;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.promotion.FlashSaleRepository;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.system.task.impl.TaskProductProcessor;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.promotion.falshSale.FlashSale;
import com.salesmanager.core.model.promotion.falshSale.FlashSaleDescription;
import com.salesmanager.core.model.promotion.falshSale.FlashSaleProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service("flashSaleServiceV3")
public class FlashSaleServiceImpl implements FlashSaleService {


    private FlashSaleRepository flashSaleRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private TaskProductProcessor taskProductProcessor;

    @Inject
    public FlashSaleServiceImpl(FlashSaleRepository flashSaleRepository) {
        this.flashSaleRepository = flashSaleRepository;
    }

    @Override
    public Integer definition(Long ownerId, Date startAt, Date endAt) throws Exception {

        FlashSale flashSale = new FlashSale();


        flashSale.setEndAt(endAt);
        flashSale.setStartAt(startAt);
        flashSale.setCreatedAt(new Date());
        flashSale.setOwnerId(ownerId);

        flashSale = flashSaleRepository.saveAndFlush(flashSale);
        return flashSale.getId();
    }

    @Transactional
    @Override
    public void combine(Integer flashSaleId, List<FlashSaleDescription> flashSaleDescriptions) throws Exception {
        //Integer flashSaleId, List<FlashSaleDescription> flashSaleDescriptions
        boolean exists = flashSaleRepository.existsById(flashSaleId);
        if (!exists) {
            throw new ServiceException("闪购活动不存在");
        }
        if (flashSaleDescriptions == null) {
            throw new ServiceException("请插入banner的描述");
        }

        FlashSale flashSale = flashSaleRepository.findFlashSaleById(flashSaleId);
        for (FlashSaleDescription description : flashSaleDescriptions) {
            //将desc 跟当前banner关联起来
            description.setFlashSale(flashSale);
            flashSale.getDescriptions().add(description);
        }
        flashSaleRepository.save(flashSale);
    }

    @Transactional
    @Override
    public void combineProducts(Integer flashSaleId, List<FlashSaleProduct> products) throws Exception {
        boolean exists = flashSaleRepository.existsById(flashSaleId);
        if (!exists) {
            throw new ServiceException("闪购活动不存在");
        }
        FlashSale flashSale = flashSaleRepository.findFlashSaleById(flashSaleId);
        products.forEach(
                flashSaleProduct -> {
                    boolean existsP = productService.existsProductsById(flashSaleProduct.getProductId());
                    if (existsP){
                        flashSaleProduct.setFlashSale(flashSale);
                    }
                }

        );
        flashSaleRepository.save(flashSale);
        //TODO 创建定时任务去开启闪购？
        taskProductProcessor.saveTaskFlashTask(products,flashSale.getStartAt().getTime(),flashSale.getEndAt().getTime());
    }

    @Transactional
    @Override
    public void delete(Integer advertiseId) {
        flashSaleRepository.deleteById(advertiseId);
    }

    @Override
    public List<FlashSale> flashSales() {
        return flashSaleRepository.findAll();
    }


    @Override
    public FlashSale flashSales(Integer id) {
        return flashSaleRepository.findById(id).orElse(null);
    }
}
