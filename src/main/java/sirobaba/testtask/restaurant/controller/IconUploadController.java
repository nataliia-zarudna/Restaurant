package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sirobaba.testtask.restaurant.model.Roles;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Nataliia on 01.08.2015.
 */
@Controller
public class IconUploadController {

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("name") String name
            , @RequestParam("file") MultipartFile file
            , ModelMap modelMap) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                //String rootPath1 = "D:\\Мои файлы\\Dropbox\\Tranings\\Spring\\CPCS\\Restaurant_test\\src\\main\\webapp\\images";
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                String filePath = dir.getAbsolutePath()
                        + File.separator + name;

                File serverFile = new File(filePath);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return "redirect:edit_menu";

            } catch (Exception e) {
                modelMap.addAttribute("errorMessage", e.getMessage());
                return "redirect:error";
            }
        } else {
            modelMap.addAttribute("errorMessage", "File is empty");
            return "redirect:error";
        }
    }
}
