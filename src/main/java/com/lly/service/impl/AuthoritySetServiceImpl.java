package com.lly.service.impl;

import com.lly.dao.IAuthoritySetDao;
import com.lly.pojo.AuthoritySetPojo;
import com.lly.pojo.PagePojo;
import com.lly.service.IAuthoritySetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangLei
 * @Description: 权限设置服务接口实现类
 * @Date: Create in 2018/3/8 16:02
 * @Modify by WangLei
 */
@Service("authoritySetService")
@Transactional(rollbackFor = Exception.class)
public class AuthoritySetServiceImpl implements IAuthoritySetService {

    @Resource
    private IAuthoritySetDao authoritySetDao;

    @Override
    public int haveAuthority(String userType,String pageCode) {
        AuthoritySetPojo authoritySetPojo = new AuthoritySetPojo();
        authoritySetPojo.setUserType(userType);
        PagePojo pagePojo = new PagePojo();
        pagePojo.setPageCode(pageCode);
        authoritySetPojo.setPagePojo(pagePojo);
        return authoritySetDao.haveAuthority(authoritySetPojo);
    }

    @Override
    public int operateAuthoritySet(Map<String,List<AuthoritySetPojo>> authoritySetPojoMap) throws Exception {
        int result = 0;
        if(authoritySetPojoMap != null && authoritySetPojoMap.size()>0){
            for (Map.Entry<String,List<AuthoritySetPojo>> entry:authoritySetPojoMap.entrySet()) {
                authoritySetDao.deleteAuthoritySet(entry.getKey());
                result += authoritySetDao.insertAuthoritySet(entry.getValue());
            }
        }
        return result;
    }

}
