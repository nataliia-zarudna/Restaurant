package sirobaba.testtask.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sirobaba.testtask.restaurant.model.entity.Group;
import sirobaba.testtask.restaurant.model.entity.User;
import sirobaba.testtask.restaurant.model.service.GroupService;

import java.io.Serializable;

/**
 * Created by Nataliia on 04.08.2015.
 */
@Service("groupEditPermissionEvaluator")
public class GroupEditPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private GroupService groupService;

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {

        if(o instanceof Integer) {

            User user = (User) authentication.getPrincipal();

            int groupID = (Integer) o;
            Group group = groupService.getGroup(groupID);

            return group.getOwnerID() == user.getId();
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
