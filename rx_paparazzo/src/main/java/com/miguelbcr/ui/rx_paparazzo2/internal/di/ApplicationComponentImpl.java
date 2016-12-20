package com.miguelbcr.ui.rx_paparazzo2.internal.di;

import com.miguelbcr.ui.rx_paparazzo2.entities.Config;
import com.miguelbcr.ui.rx_paparazzo2.entities.TargetUi;
import com.miguelbcr.ui.rx_paparazzo2.interactors.CropImage;
import com.miguelbcr.ui.rx_paparazzo2.interactors.DownloadImage;
import com.miguelbcr.ui.rx_paparazzo2.interactors.GetDimens;
import com.miguelbcr.ui.rx_paparazzo2.interactors.GetPath;
import com.miguelbcr.ui.rx_paparazzo2.interactors.GrantPermissions;
import com.miguelbcr.ui.rx_paparazzo2.interactors.ImageUtils;
import com.miguelbcr.ui.rx_paparazzo2.interactors.PickFile;
import com.miguelbcr.ui.rx_paparazzo2.interactors.PickFiles;
import com.miguelbcr.ui.rx_paparazzo2.interactors.PickImage;
import com.miguelbcr.ui.rx_paparazzo2.interactors.PickImages;
import com.miguelbcr.ui.rx_paparazzo2.interactors.SaveImage;
import com.miguelbcr.ui.rx_paparazzo2.interactors.StartIntent;
import com.miguelbcr.ui.rx_paparazzo2.interactors.TakePhoto;
import com.miguelbcr.ui.rx_paparazzo2.workers.Camera;
import com.miguelbcr.ui.rx_paparazzo2.workers.Files;
import com.miguelbcr.ui.rx_paparazzo2.workers.Gallery;

class ApplicationComponentImpl extends ApplicationComponent {
  private final ImageUtils imageUtils;
  private final DownloadImage downloadImage;
  private final StartIntent startIntent;
  private final GetPath getPath;
  private final GetDimens getDimens;
  private final TakePhoto takePhoto;
  private final CropImage cropImage;
  private final SaveImage saveImage;
  private final GrantPermissions grantPermissions;
  private final PickImages pickImages;
  private final PickImage pickImage;
  private final Camera camera;
  private final Gallery gallery;
  private final Files files;

  public ApplicationComponentImpl(TargetUi ui, Config config) {
    startIntent = new StartIntent(ui);
    imageUtils = new ImageUtils(ui, config);
    downloadImage = new DownloadImage(ui, imageUtils);
    getPath = new GetPath(ui, downloadImage);
    takePhoto = new TakePhoto(startIntent, ui, imageUtils);
    getDimens = new GetDimens(ui, config, getPath);
    cropImage = new CropImage(ui, config, startIntent, getPath, imageUtils);
    saveImage = new SaveImage(ui, getPath, getDimens, imageUtils);
    grantPermissions = new GrantPermissions(ui);
    pickImages = new PickImages(startIntent);
    pickImage = new PickImage(startIntent, getPath);
    camera = new Camera(takePhoto, cropImage, saveImage, grantPermissions, ui, config);
    gallery = new Gallery(grantPermissions, pickImages, pickImage, cropImage, saveImage, ui, config);
    files = new Files(grantPermissions, startIntent, getPath, cropImage, saveImage, ui, config);
  }

  @Override public Camera camera() {
    return camera;
  }

  @Override public Gallery gallery() {
    return gallery;
  }

  @Override public GetPath getPath() {
    return getPath;
  }

  @Override public Files files() {
    return files;
  }
}