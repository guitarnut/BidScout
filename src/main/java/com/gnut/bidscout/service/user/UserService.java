package com.gnut.bidscout.service.user;

import com.gnut.bidscout.db.UserAccountStatisticsDao;
import com.gnut.bidscout.db.UsersDao;
import com.gnut.bidscout.model.UserAccountStatistics;
import com.gnut.bidscout.model.UserProfile;
import com.gnut.bidscout.model.Users;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UserService {
    private final BCryptPasswordEncoder encoder;
    private final UsersDao usersDao;
    private final UserAccountStatisticsDao statisticsDao;

    @Autowired
    public UserService(
            UsersDao usersDao,
            UserAccountStatisticsDao statisticsDao
    ) {
        this.usersDao = usersDao;
        this.statisticsDao = statisticsDao;
        encoder = new BCryptPasswordEncoder();
    }

    public Users login(Users u, HttpServletRequest request, HttpServletResponse response) {
        final Users user = usersDao.findByUsername(u.getUsername());
        if (user != null && user.isEnabled()) {
            if (encoder.matches(u.getPassword(), user.getPassword())) {
                user.setLastLogin(System.currentTimeMillis());
                String ip = request.getHeader("X-Forwarded-For");
                if (!Strings.isNullOrEmpty(ip) && !user.getIpAccess().contains(ip)) {
                    user.getIpAccess().add(ip);
                }
                Users saved = usersDao.save(user);
                Users userData = new Users();
                userData.setId(saved.getId());
                userData.setUsername(user.getUsername());
                return userData;
            } else {
                String ip = request.getHeader("X-Forwarded-For");
                if (!Strings.isNullOrEmpty(ip) && !user.getIpAccess().contains(ip)) {
                    user.getIpAccess().add(ip);
                }
                user.setFailedLoginAttemptCount(user.getFailedLoginAttemptCount() + 1);
                usersDao.save(user);
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return null;
    }

    public Users createUser(Users user, HttpServletResponse response) {
        if (usersDao.findByUsername(user.getUsername()) != null) {
            response.setStatus(204);
            return null;
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRoles(Arrays.asList(Users.Role.ADMIN, Users.Role.USER));
            user.setCreated(System.currentTimeMillis());
            user.setEnabled(true);
            Users u = usersDao.save(user);
            UserAccountStatistics statistics = new UserAccountStatistics();
            statistics.setUser(u.getId());
            statisticsDao.save(statistics);
            Users userData = new Users();
            userData.setUsername(user.getUsername());
            return userData;
        }
    }

    public void deleteUser(Authentication id) {
        Optional<Users> u = usersDao.findById(getAccount(id));
        if (u.isPresent()) {
            usersDao.delete(u.get());
        }
    }

    public UserProfile updateUser(Authentication auth, UserProfile updatedUser) {
        Optional<Users> user = usersDao.findById(getAccount(auth));
        if (user.isPresent()) {
            user.get().update(updatedUser);
            usersDao.save(user.get());
        }
        return updatedUser;
    }

    public UserProfile getUser(Authentication auth) {
        Optional<Users> u = usersDao.findById(getAccount(auth));
        if (u.isPresent()) {
            return new UserProfile(u.get());
        } else {
            return null;
        }
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
