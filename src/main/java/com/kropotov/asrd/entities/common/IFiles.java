package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.docs.File;

public interface IFiles {
  boolean  addFile(File file);
  boolean removeFile(File file);
}
