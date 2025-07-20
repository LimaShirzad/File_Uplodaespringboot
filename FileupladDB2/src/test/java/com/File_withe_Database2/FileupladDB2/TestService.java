package com.File_withe_Database2.FileupladDB2;


import com.File_withe_Database2.FileupladDB2.Model.Uploade_File;
import com.File_withe_Database2.FileupladDB2.Repository.UploadedFileRepository;
import com.File_withe_Database2.FileupladDB2.Service.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestService {

    @InjectMocks
    ImageService imageService;     // mockito depancy injection to servr class means when you need to uplode file  it will get from @mock its facke

    @Mock  // making fake repository not real database
    UploadedFileRepository repo;

    @Test
    void saveImageWorks() throws Exception {
        var file = mock(MultipartFile.class);  //
        when(file.getOriginalFilename()).thenReturn("a.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenReturn("123".getBytes());

        imageService.saveImage(file);

        verify(repo).save(any());
    }

    @Test
    void getAllRecords_ShouldReturnList()
    {
        List<Uploade_File> mockList=List.of(new Uploade_File());

        when(repo.findAll()).thenReturn(mockList);   // return thid list not real dataase
//        Act

//        Like repo.findAll
        List<Uploade_File> result=imageService.getAllRecords();

        assertEquals(1,result.size());

        verify(repo).findAll();

    }


    @Test
    void testdeleteimage()
    {
            Long FileId=1L;

            repo.deleteById(FileId);

            verify(repo).deleteById(FileId);
    }
    @Test
   void testgetFileById_sucess() throws  Exception{

        Uploade_File file =new Uploade_File();

        file.setId(1L);

        file.setFile_name("test.jpg");

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(file));

        Uploade_File result=imageService.getFileById(1L);

        assertEquals(1L,result.getFile_name());


    }

    @Test
    void testgetFileById_notFound()
    {

          Mockito.when(repo.findById(2L)).thenReturn(Optional.empty());

          try{

              imageService.getFileById(2L);
              fail("Expected");

          }catch (RuntimeException e)
          {
                                assertEquals("file not found",e.getMessage());
          }



    }





}

