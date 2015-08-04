package sirobaba.testtask.restaurant.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import sirobaba.testtask.restaurant.model.Roles;
import sirobaba.testtask.restaurant.model.service.MenuService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 01.08.2015.
 */
@Controller
public class IconUploadController {

    public static final Logger log = Logger.getLogger(IconUploadController.class.getName());

    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private MenuService menuService;

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/uploadIcon", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("file") MultipartFile file
            , @RequestParam(value = "dishID", defaultValue = "-1", required = false) int dishID
            , ModelMap modelMap) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                String pathToImagesDir = requestAttributes.getRequest().getRealPath("/images");

                File dir = new File(pathToImagesDir);
                if (!dir.exists())
                    dir.mkdirs();

                String fileName = file.getOriginalFilename();
                String filePath = dir.getAbsolutePath()
                        + File.separator + fileName;

                File serverFile = new File(filePath);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                if(dishID > 0) {
                    menuService.updateDishIcon(dishID, fileName);
                }

                return "redirect:" + PageNames.EDIT_MENU;

            } catch (Exception e) {
                return errorHandler.handle(modelMap, log, e);
            }
        } else {
            modelMap.addAttribute("errorMessage", "File is empty");
            return "redirect:" + PageNames.ERROR;
        }
    }

}
