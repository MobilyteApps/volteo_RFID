import 'package:apprfid/native/flutter_volteo_plugin.dart';
import 'package:apprfid/rssibarview/bar_view.dart';
import 'package:apprfid/rssibarview/spritedemo.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<DeviceDescription> listBD = [];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Locate",
          style: TextStyle(fontSize: 16.0, color: Colors.black),
        ),
        backgroundColor: Colors.white,
        iconTheme: IconThemeData(color: Colors.red),
        actions: <Widget>[
          // IconButton(
          //   icon: Icon(Icons.play_circle_outline),
          //   tooltip: 'Start Discovery',
          //   onPressed: () {
          //     FlutterVolteoPlugin.init();
          //   },
          // ),
          // IconButton(
          //   icon: Icon(Icons.devices),
          //   tooltip: 'Devices List',
          //   onPressed: () => {},
          // ),
          IconButton(
            icon: Icon(Icons.list),
            tooltip: 'Find New Devices',
            onPressed: () async {
               listBD = await FlutterVolteoPlugin.availableDevices();
              print("list of devices $listBD");
              setState(() {});
              showDevicesList(context);
            },
          ),
        ],
      ),
      body: Center(
        child: content()
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  Widget renderList() {
    if (this.listBD != null && this.listBD.length > 0) {
      return ListView.builder(
        itemCount: this.listBD.length,
        itemBuilder: (context, index) {
          return ListTile(
              title: Text('${this.listBD[index].name}'),
              onTap: () async {
                await FlutterVolteoPlugin.connectToDevice(
                    this.listBD[index].address);
                Navigator.of(context).pop();
              });
        },
      );
    } else {
      return Center(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              SpriteDemo(),
              Text("Please wait finding devices ...")
            ],
          ));
    }
  }
  showDevicesList(BuildContext ctx) {
    showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: Text('Choose device to connect'),
            content: Container(
              width: double.maxFinite,
              height: 300.0,
              child: renderList(),
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('CANCEL'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              )
            ],
          );
        });
  }
  Widget content() {
    return Padding(
        padding: EdgeInsets.only(top: 60.00),
        child: Column(children: <Widget>[
          Container(
            margin: EdgeInsets.fromLTRB(0.0, 10.0, 0.0, 10.0),
            height: 200.00,
            color: Colors.white,
            child: BarView(),
          ),
//          renderAsset(),
//          Expanded(child: renderDataList())
        ]));
  }
}
