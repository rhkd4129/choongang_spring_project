package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.BdFreeDao;
import com.oracle.s202350101.model.BdFree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BdFreeServiceImpl implements BdFreeService {

    private final BdFreeDao BFdao;
    @Override
    public List<BdFree> findAllBdFree() {
        return BFdao.findAllBdFree();
    }

    @Override
    public List<BdFree> findBdFreeByCategory(BdFree bf) {


        return BFdao.findBdFreeByCategory(bf);
    }

    @Override
    public List<BdFree> pageBdFreeByCategoryAndPage(BdFree bf) {
        return BFdao.pageBdFreeByCategoryAndPage(bf);
    }


}
