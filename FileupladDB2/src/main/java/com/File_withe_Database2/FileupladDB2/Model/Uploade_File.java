package com.File_withe_Database2.FileupladDB2.Model;



import jakarta.persistence.*;

@Entity
@Table(name="uploaded_file")
public class Uploade_File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String file_name;

    private String Filetype;

    @Lob
    private  byte[] data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return Filetype;
    }

    public void setFile_type(String file_type) {
        this.Filetype = file_type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
