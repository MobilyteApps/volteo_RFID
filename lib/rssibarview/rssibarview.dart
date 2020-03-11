import 'dart:ui';

import 'package:flutter/material.dart';


class RssiBarView extends CustomPainter {
  final double height;
  final double borderWidth;

  final double barWidthScale;
  final double maxBarWidthScale;
  final Color axisMajorColor;
  final Color axisMinorColor;
  final Color barHighColor;
  final Color barMediumColor;
  final Color barLowerColor;

  Paint _mainPaint = new Paint();
  double margin = 8.0;
  Rect _rect;

  RssiBarView({
    this.height = 00.0,
    this.borderWidth = 0.0,
    this.barWidthScale = 8.0,
    this.maxBarWidthScale = 0.9,
    this.axisMajorColor = const Color.fromRGBO(100, 100, 100, 1.0),
    this.axisMinorColor = const Color.fromRGBO(140, 140, 140, 1.0),
    this.barHighColor = const Color.fromRGBO(242, 0, 0, 0.5),
    this.barMediumColor = const Color.fromRGBO(190, 190, 0, 0.5),
    this.barLowerColor = const Color.fromRGBO(0, 130, 0, 0.5),
  }) : super();

  @override
  void paint(Canvas canvas, Size size) {
    this._rect =
        Rect.fromLTWH(0.0, 200.00 - this.height, size.width, this.height);
    init(canvas, size);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return true;
  }

  init(Canvas canvas, Size size) {
    //double top = 0.0;
    List<Color> iArr = [
      this.barHighColor,
      this.barMediumColor,
      this.barLowerColor
    ];
    List<double> fArr3 = [0.1, 0.4, 1.0];
    if (this.height <= 0.0) {
      this._mainPaint..shader = null;
      this._mainPaint..color = this.barLowerColor;
    } else {
      this._mainPaint
        ..shader = new LinearGradient(
          colors: iArr,
          tileMode: TileMode.clamp,
          stops: fArr3,
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
        ).createShader(_rect);
    }
    this._mainPaint..style = PaintingStyle.fill;
    this._mainPaint..isAntiAlias = true;

    canvas.drawRect(_rect, this._mainPaint);
  }
}
