import 'package:flutter/material.dart';
import 'package:apprfid/rssibarview/lines_painter.dart';
import 'package:apprfid/rssibarview/rssibarview.dart';
import 'dart:async';
import 'dart:math' as Math;

class RssiBarViewExample extends StatefulWidget {
  RssiBarViewExample({Key key}) : super(key: key);

  @override
  _RssiBarViewExampleState createState() => _RssiBarViewExampleState();
}

class _RssiBarViewExampleState extends State<RssiBarViewExample> {
  RandomNumber randomNumber = RandomNumber();
  double height = 0.0;

  @override
  void initState() {
    final randomNumStream = randomNumber.stream;
    final subscription = randomNumStream.listen(
        (data) {
          print('Data: $data');
          this.setState(() {
            height = data.toDouble();
          });
        },
        onError: (err) {
          print('Error: $err');
        },
        cancelOnError: false,
        onDone: () {
          print('Done');
        });
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      child: Center(
        child: Container(
          color: Colors.transparent,
          child: CustomPaint(
              size: Size(100, 200),
              painter: Drawhorizontalline(),
              foregroundPainter: RssiBarView(height: this.height),
              child: Container(width: 100.00, height: 200.00)),
        ),
      ),
    );
  }

  @override
  void dispose() {
    randomNumber.dispose();
    // TODO: implement dispose
    super.dispose();
  }
}

class RandomNumber {
  final StreamController _controller = StreamController<int>();
  int _count = Math.Random().nextInt(200);
  Timer _timer;

  RandomNumber() {
    Timer.periodic(Duration(seconds: 1), (timer) {
      _timer = timer;
      _controller.sink.add(_count);
      _count = Math.Random().nextInt(200);
    });
  }
  Stream<int> get stream => _controller.stream;

  dispose() {
    _timer.cancel();
    _controller.sink.close();
  }
}
