declare var pushLatlon;

var historicalOverlay;
var imageBounds;

/**

  . (north, west)
   
              .(south, east)
 */
function initOverlayMap(map, area,moveAgent) {
  console.log("initOverlayMap", area);
  // var imageBounds = { 
  //   north: 36.108622,
  //   south: 36.10746,
  //   east: 129.425499,
  //   west: 129.422656
  // };

  // var imageBounds = {
  //   north: 36.11869417,
  //   south: 36.11662266,
  //   east: 129.4178638,
  //   west: 129.4151017
  // };

  //  var imageBounds = {
  //    north: 36.12041773,
  //    south: 36.11834622,
  //    east: 129.4183088,
  //    west: 129.4155468
  //  };

  if (historicalOverlay) {
    historicalOverlay.setMap(null);
    historicalOverlay = null;
  }

  if (area === "ph")
    imageBounds = {
      north: 36.1201151948418,
      south: 36.1183173394595,
      east: 129.417518991423,
      west: 129.414434951001,
    };
  // 포항
  // imageBounds = { north: 35.10646751823057, south: 35.105238715280514, east: 126.89681121628573, west: 126.89411827843477 }; // 포항
  else
    imageBounds = {
      north: 35.246063,
      south: 35.243093,
      east: 126.837505,
      west: 126.833736,
    }; // 포항

  var overlayOpts = {
    opacity: 0.9,
  };
  //historicalOverlay = new google.maps.GroundOverlay("/monitoring/resources/images/pohang-map.jpg", imageBounds, overlayOpts);
  //historicalOverlay = new google.maps.GroundOverlay("/monitoring/resources/images/pohang-map-rotate1.png", imageBounds, overlayOpts);
  //historicalOverlay = new google.maps.GroundOverlay("/monitoring/resources/images/pohang-map-rotate2.png", imageBounds, overlayOpts);
  if (area === "ph") historicalOverlay = new google.maps.GroundOverlay("/monitoring/resources/images/pohang-map-13630.png", imageBounds, overlayOpts);
  // 포항
  else historicalOverlay = new google.maps.GroundOverlay("/monitoring/resources/images/gwangju-map-2048.png", imageBounds, overlayOpts); //광주
  // console.log("historicalOverlay", historicalOverlay);

  historicalOverlay.setMap(map);

  historicalOverlay.addListener("click", function (event) {
    var latitude = event.latLng.lat();
    var longitude = event.latLng.lng();
	let menu;
	clearRobotSelection();
	moveAgent(area,parseFloat(latitude),parseFloat(longitude));
	setMoveAgentCursor(false);
    pushLatlon({
      lat: parseFloat(latitude).toFixed(6),
      lon: parseFloat(longitude).toFixed(6),
    });
  });

  map.fitBounds(imageBounds);
  /*
  let marker = new google.maps.Marker({
    id: "1",
    position: {
      lat: 36.118339,
      lng: 129.414793
    },
    icon: {
      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
    },
    label: {
      text: "1",
      color: "#0000ff",
      fontWeight: "bold"
    }
  });
  marker.setMap(map);

  marker = new google.maps.Marker({
    id: "2",
    position: {
      lat: 36.118739,
      lng: 129.41554
    },
    icon: {
      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
    },
    label: {
      text: "2",
      color: "#0000ff",
      fontWeight: "bold"
    }
  });
  marker.setMap(map);

  marker = new google.maps.Marker({
    id: "3",
    position: {
      lat: 36.11905,
      lng: 129.415855
    },
    icon: {
      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
    },
    label: {
      text: "3",
      color: "#0000ff",
      fontWeight: "bold"
    }
  });
  marker.setMap(map);

  marker = new google.maps.Marker({
    id: "4",
    position: {
      lat: 36.118562,
      lng: 129.416534
    },
    icon: {
      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
    },
    title: "4",
    label: {
      text: "4",
      color: "#0000ff",
      fontWeight: "bold"
    }
  });
  marker.setMap(map);
  */

  /*
   * 36.11842026	129.4159467
36.11873257	129.4155372
36.11900224	129.4158275
36.11856314	129.4165402

   */

  //  let marker = new google.maps.Marker({
  //    id: "1",
  //    position: { lat: 36.11842026, lng: 129.4159467 },
  //    icon: {
  //      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
  //    },
  //    label: {
  //      text: "1",
  //      color: "#0000ff",
  //      fontWeight: "bold"
  //    }
  //  });
  //  marker.setMap(map);
  //
  //  marker = new google.maps.Marker({
  //    id: "2",
  //    position: { lat: 36.11873257, lng: 129.4155372 },
  //    icon: {
  //      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
  //    },
  //    label: {
  //      text: "2",
  //      color: "#0000ff",
  //      fontWeight: "bold"
  //    }
  //  });
  //  marker.setMap(map);
  //
  //  marker = new google.maps.Marker({
  //    id: "3",
  //    position: { lat: 36.11900224, lng: 129.4158275 },
  //    icon: {
  //      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
  //    },
  //    label: {
  //      text: "3",
  //      color: "#0000ff",
  //      fontWeight: "bold"
  //    }
  //  });
  //  marker.setMap(map);
  //
  //  marker = new google.maps.Marker({
  //    id: "4",
  //    position: { lat: 36.11856314, lng: 129.4165402 },
  //    icon: {
  //      url: "".concat("/monitoring/resources/images/map/marker1-1.png")
  //    },
  //    title: "4",
  //    label: {
  //      text: "4",
  //      color: "#0000ff",
  //      fontWeight: "bold"
  //    }
  //  });
  //  marker.setMap(map);
}
