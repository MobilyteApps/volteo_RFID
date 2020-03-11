import 'package:flutter/material.dart';

class Drawhorizontalline extends CustomPainter {
  Paint _paint;
  final int axisNumberOfDivisions;
  final int axisMajorDivisionStep;
  final double axisStrokeWidth;

  Drawhorizontalline({
    this.axisNumberOfDivisions = 200,
    this.axisMajorDivisionStep = 10,
    this.axisStrokeWidth = 1.0,
  }) : super() {
    _paint = Paint()
      ..color = Colors.grey
      ..strokeWidth = 1
      ..strokeCap = StrokeCap.round;
  }

  @override
  void paint(Canvas canvas, Size size) {
    int i = 0;
    while (i <= this.axisNumberOfDivisions) {
      // bool isEven = i % 2 == 0;
      canvas.drawLine(
          Offset(0.0, 0.0 + i), Offset(size.width, 0.0 + i), _paint);
      i += this.axisMajorDivisionStep;
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return false;
  }
}
