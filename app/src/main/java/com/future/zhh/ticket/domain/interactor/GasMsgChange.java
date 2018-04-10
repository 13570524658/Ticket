package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GasMsgChange extends Case {
    private CommonRepository repository;
    private String id;
    private String  gasLabe;
    private String productFactoryCompany;
    private String productFactoryID;
    private String gasweight;
    private String gasModule;
    private String gasSteelcode;
    private String productData;
    private String lastTestCompany;
    private String lastTestData;
    private String nextTestData;
    private String image1;
    private String image2;
    private String image3;



    @Inject
    public GasMsgChange(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                           CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }

    public void setData(String id,String gasLabe,String productFactoryCompany,String productFactoryID,String gasweight,String gasModule,String gasSteelcode,
                        String productData,String lastTestCompany,String lastTestData,String nextTestData,
                        String image1,String image2,String image3){
        this.id=id;
        this.gasLabe = gasLabe;
        this.productFactoryCompany=productFactoryCompany;
        this.productFactoryID=productFactoryID;
        this.gasweight=gasweight;
        this.gasModule=gasModule;
        this.gasSteelcode=gasSteelcode;
        this.productData=productData;
        this.lastTestCompany=lastTestCompany;
        this.lastTestData=lastTestData;
        this.nextTestData=nextTestData;
        this.image1=image1;
        this.image2=image2;
        this.image3=image3;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.gasMsgChange(id,gasLabe,productFactoryCompany,productFactoryID,gasweight,gasModule,gasSteelcode,productData,
                lastTestCompany,lastTestData,nextTestData,image1,image2,image3);
    }
}
