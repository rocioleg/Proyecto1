package com.example.miprimerproyecto

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class CamaraDCheck : AppCompatActivity() {

    lateinit var capReq: CaptureRequest.Builder

    lateinit var handler: Handler
    lateinit var handlerThread: HandlerThread

    lateinit var cameraManager: CameraManager
    lateinit var textureView: TextureView
    lateinit var cameraCaptureSession: CameraCaptureSession
    lateinit var cameraDevice: CameraDevice
    lateinit var cameraRequest: CaptureRequest

    lateinit var imageReader: ImageReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara_dcheck)
        get_permissions()

        textureView = findViewById(R.id.textureViewCF)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler((handlerThread).looper)

        if(hasFrontCamera()){

            textureView.surfaceTextureListener = object: TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                open_camera()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
            }

        }} else {
            //
            Toast.makeText(this, "No se encontró una cámara frontal.", Toast.LENGTH_SHORT).show()
        }

        imageReader = ImageReader.newInstance(1080,1920, ImageFormat.JPEG, 1)
        imageReader.setOnImageAvailableListener(object: ImageReader.OnImageAvailableListener{
            override fun onImageAvailable(p0: ImageReader?) {

                var image = p0?.acquireLatestImage()
                var buffer = image!!.planes!![0].buffer
                var bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)

                var file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "img.jpeg")
                var opStream = FileOutputStream(file)

                opStream.write(bytes)

                opStream.close()
                image.close()

                Toast.makeText(this@CamaraDCheck,"imagen capturada", Toast.LENGTH_SHORT).show()
            }

        }, handler)

        findViewById<Button>(R.id.btnCapturaD).apply {
            setOnClickListener{
                capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                capReq.addTarget(imageReader.surface)
                cameraCaptureSession.capture(capReq.build(),null,null)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun open_camera(){

        cameraManager.openCamera(cameraManager.cameraIdList[1], object: CameraDevice.StateCallback(){
            override fun onOpened(p0: CameraDevice) {
              cameraDevice = p0

              var capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)

              var surface = Surface(textureView.surfaceTexture)
              capReq.addTarget(surface)

              cameraDevice.createCaptureSession(listOf(surface,imageReader.surface),object: CameraCaptureSession.StateCallback(){
                  override fun onConfigured(p0: CameraCaptureSession) {
                    cameraCaptureSession = p0
                    cameraCaptureSession.setRepeatingRequest(capReq.build(),null,null)
                  }

                  override fun onConfigureFailed(p0: CameraCaptureSession) {

                  }

              }, handler )

            }

            override fun onDisconnected(p0: CameraDevice) {

            }

            override fun onError(p0: CameraDevice, p1: Int) {

            }

        }, handler)
    }

    fun get_permissions(){
        var permissionsList = mutableListOf<String>()
        if(checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.CAMERA)
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permissionsList.size > 0){
            requestPermissions(permissionsList.toTypedArray(), 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if( it != PackageManager.PERMISSION_GRANTED){
                get_permissions()
            }
        }
    }

    private fun hasFrontCamera(): Boolean {
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        for (cameraId in manager.cameraIdList) {
            val characteristics = manager.getCameraCharacteristics(cameraId)
            val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
            if (facing == CameraCharacteristics.LENS_FACING_FRONT) {
                return true // Hay una cámara frontal disponible.
            }
        }
        return false // No hay cámara frontal disponible.
    }


}