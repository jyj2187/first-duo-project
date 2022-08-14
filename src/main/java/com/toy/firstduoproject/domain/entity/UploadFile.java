package com.toy.firstduoproject.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {
   private String originalFilename;
   private String storedFilename;

   public UploadFile(String originalFilename, String storedFilename) {
      this.originalFilename = originalFilename;
      this.storedFilename = storedFilename;
   }
}
