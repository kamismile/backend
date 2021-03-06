package com.blocain.bitms.wallet.thread;

import com.blocain.bitms.payment.eth.EthereumUtils;
import com.blocain.bitms.tools.utils.LoggerUtils;
import com.blocain.bitms.trade.fund.entity.EthAddrs;
import com.blocain.bitms.wallet.ERC20BlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.List;

/**
 * ETH有效地址库信息扫描线程
 */
@Component
public class ScanEthAddrsThread implements Runnable
{
    private static Logger     logger    = LoggerFactory.getLogger(ScanEthAddrsThread.class);
    
    @Autowired
    private ERC20BlockService eRC20BlockService;

    private boolean           isRunning = true;
    
    @Override
    public void run()
    {
        // BigInteger iStartNum = new BigInteger("0");
        BigInteger iStartNum = new BigInteger("5500000");
//        List<EthAddrs> listEthAddrs = eRC20BlockService.findListMinHeightEthAddrs();
//        if(null != listEthAddrs && listEthAddrs.size() > 0){
//            LoggerUtils.logInfo(logger, "EthAddrs高度开始BlockHeight:" + listEthAddrs.get(0).getBlockHeight());
//            iStartNum = BigInteger.valueOf(listEthAddrs.get(0).getBlockHeight()).add(BigInteger.ONE);
//            LoggerUtils.logInfo(logger, "最原始高度开始iStartNum:" + iStartNum);
//        }
        iStartNum = iStartNum.add(BigInteger.ONE);
        while (isRunning)
        {
            try
            {
                LoggerUtils.logDebug(logger, "启动ETH有效地址库信息扫描线程服务 ===============");
                iStartNum = iStartNum.subtract(BigInteger.ONE);
                LoggerUtils.logInfo(logger, "iStartNum:" + iStartNum);
                EthBlock.Block ethBlock = EthereumUtils.ethGetBlockByNumber(iStartNum, false);
                LoggerUtils.logDebug(logger, "ethBlock:" + ethBlock.toString());
                if (null != ethBlock)
                {
                    eRC20BlockService.doScanEthAddrs(ethBlock);
                }
                LoggerUtils.logDebug(logger, "结束ETH有效地址库信息扫描线程服务 ===============");
            }
            catch (Exception e)
            {
                LoggerUtils.logError(logger, "ETH有效地址库信息扫描线程服务失败：{}", e.getLocalizedMessage());
            }
            finally
            {
//                try
//                {
//                    Thread.sleep(500);
//                }
//                catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
            }
        }
    }
    
    public void setRunning(boolean running)
    {
        isRunning = running;
    }
}
