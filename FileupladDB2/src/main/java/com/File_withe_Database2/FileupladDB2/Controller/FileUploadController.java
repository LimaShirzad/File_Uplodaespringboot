package com.File_withe_Database2.FileupladDB2.Controller;

import com.File_withe_Database2.FileupladDB2.Model.Uploade_File;
import com.File_withe_Database2.FileupladDB2.Repository.UploadedFileRepository;
import com.File_withe_Database2.FileupladDB2.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class FileUploadController {

    @Autowired
    ImageService imageService;
//    private UploadedFileRepository repository;


    @GetMapping("/upload")
    public String showForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {


        try {

            imageService.saveImage(file);

            return "sucess";

        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/All_record")
    public String view_allRecord(Model model) {

        List<Uploade_File> images = imageService.getAllRecords();

        model.addAttribute("images", images);

        return "viewAllRecord";
    }

    @GetMapping("/images/{id}/data")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        try {

            Uploade_File image = imageService.getFileById(id);

            byte[] data = image.getData();

            String filetype = image.getFile_type();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.parseMediaType(filetype));

            return new ResponseEntity<>(data, headers, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.notFound().build();

        }
    }




    @GetMapping("/images/view/{id}")
    public String viewImge(@PathVariable Long id, Model model) {

        try {

            Uploade_File image = imageService.getFileById(id);

            model.addAttribute("image", image);
            return "view_image";

        } catch (Exception e) {
            return "error";
        }

    }

    @GetMapping("/images/delete/{id}")
    public String deleteimage(@PathVariable Long id) {

        imageService.deleteImageById(id);

        return "redirect:/All_record";

    }

    //
    @GetMapping("/images/update/{id}")
    public String showupdateForm(@PathVariable Long id, Model model) {

        try {

            Uploade_File image = imageService.getFileById(id);

            model.addAttribute("image", image);

            return "update";


        } catch (Exception e) {

            return "redirect:/All_record";

        }

    }


    @PostMapping("/images/update/{id}")
    public String updateImage(@PathVariable Long id,
                              @RequestParam("name") String name,
                              @RequestParam(value = "file", required = false)
                              MultipartFile file)
            throws IOException {

        imageService.updateFile(id, name, file);


        return "redirect:/All_record";
    }


}
