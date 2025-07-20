package com.File_withe_Database2.FileupladDB2.Service;


import com.File_withe_Database2.FileupladDB2.Model.Uploade_File;
import com.File_withe_Database2.FileupladDB2.Repository.UploadedFileRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    UploadedFileRepository uploadedFileRepository;


    public  void deleteImageById(Long id)
    {

           uploadedFileRepository.deleteById(id);

    }

    public  void saveImage(MultipartFile file) throws  Exception
    {

        Uploade_File uploadeFile=new Uploade_File();

         uploadeFile.setFile_name(file.getOriginalFilename());
//         uploadeFile.setFile_name(file.getOriginalFilename());
         uploadeFile.setFile_type(file.getContentType());
         uploadeFile.setData(file.getBytes());

         uploadedFileRepository.save(uploadeFile);


    }


    public List<Uploade_File> getAllRecords()
    {

            return   uploadedFileRepository.findAll();
    }



    public  Uploade_File getFileById(Long id)
    {

        return  uploadedFileRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("file not found" +id));


    }


    public void updateFile(Long id , String name,MultipartFile file) throws IOException{

        Uploade_File image=getFileById(id);
        image.setFile_name(name);
        if(file != null && !file.isEmpty())
        {
                   image.setData(file.getBytes());
                   image.setFile_type(file.getContentType());
        }

              uploadedFileRepository.save(image);



    }







}
