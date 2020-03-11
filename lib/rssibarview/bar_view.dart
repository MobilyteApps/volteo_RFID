import 'dart:async';

import 'package:apprfid/native/flutter_volteo_plugin.dart';
import 'package:flutter/material.dart';
import 'package:apprfid/rssibarview/lines_painter.dart';
import 'package:apprfid/rssibarview/rssibarview.dart';
import 'dart:math' as math;

class BarView extends StatefulWidget {
  BarView({
    Key key,
  }) : super(key: key);

  @override
  _BarViewState createState() => _BarViewState();
}

class _BarViewState extends State<BarView> {
  StreamSubscription streamSubscription;
  double _start = 0.00;
  Color barHighColor = Color.fromARGB(230, 242, 0, 0);
  Color barMediumColor = Color.fromARGB(230, 190, 190, 0);
  Color barLowerColor = Color.fromARGB(230, 190, 190, 0);

  @override
  void initState() {
    super.initState();
    streamSubscription = FlutterVolteoPlugin.rssi_stream
        .receiveBroadcastStream()
        .listen((data) async {
      if (data != null) {
        _start = data;
        setPowerBarColour(_start);
        this.setState(() {});
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: Alignment.bottomCenter,
      children: <Widget>[
        CustomPaint(
            size: Size(100, 200),
            painter: Drawhorizontalline(),
            foregroundPainter: RssiBarView(
              height: _start * 2 * 100,
              // barHighColor: this.barHighColor,
              // barMediumColor: this.barMediumColor,
              // barLowerColor: this.barLowerColor,
            ),
            child: Container(width: 100.00, height: 200.00)),
        _drawText()
      ],
    );
  }

  void setPowerBarColour(double mCurrentReading) {
    // barMediumColor = barHighColor;
    // barLowerColor = barHighColor;
    // if (mCurrentReading > 80) {
    //   barHighColor = Color.fromARGB(230, 224, 0, 0);
    // } else {
    //   barHighColor = Color.fromARGB(230, 0, 192, 0);
    // }
  }

  Widget _drawText() {
    return Align(
      alignment: Alignment.topCenter,
      child: Container(
        child: Text(
          "${(_start * 100).round()}%",
          style: TextStyle(
              fontSize: 18.00,
              color: Colors.black,
              fontWeight: FontWeight.w600),
        ),
      ),
    );
  }

  @override
  void dispose() {
    if (streamSubscription != null) {
      streamSubscription.cancel();
      streamSubscription = null;
    }
    super.dispose();
  }
}
