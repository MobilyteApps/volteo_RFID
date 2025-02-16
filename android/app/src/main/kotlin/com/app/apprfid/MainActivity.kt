package com.app.apprfid

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity: FlutterActivity() {
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        val shimPluginRegistry = ShimPluginRegistry(flutterEngine)
        FlutterVolteoPlugin.registerWith(shimPluginRegistry.registrarFor("com.mobilyte.volteo.volteopluginmobilyte.FlutterVolteoPlugin"))
    }
}
