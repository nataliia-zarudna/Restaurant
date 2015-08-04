package sirobaba.testtask.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sirobaba.testtask.restaurant.model.Roles;

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

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("name") String name
            , @RequestParam("file") MultipartFile file
                                    , HttpServletRequest request
            , ModelMap modelMap) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String contextPath = request.getContextPath();

                String rootPath = System.getProperty("catalina.home");
                //String rootPath = "D:\\sql";
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                name = (name == null || name.isEmpty()) ? file.getOriginalFilename() : name;

                String filePath = dir.getAbsolutePath()
                        + File.separator + name;

                File serverFile = new File(filePath);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

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
