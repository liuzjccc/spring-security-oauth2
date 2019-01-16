package com.liuzj.oauth2server.config.selfauthor;

import com.liuzj.oauth2server.domain.User;
import com.liuzj.oauth2server.repositories.UserRepository;
import com.liuzj.oauth2server.utils.MD5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 进行登录用户自定义过滤
 *
 * @author liuzj
 * @date 2019-01-15
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        logger.info("loadUserByUsername username=" + username);

        if(user == null){
            throw new UsernameNotFoundException(username + " not found");
        }


        //creating dummy user details, should do JDBC operations
        return new UserInfo(username, MD5Util.encodeMD5(user.getPassword()),user.getRole());
    }

}
