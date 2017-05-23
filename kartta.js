function turku_map() {

  //console.log('yritetään tehdä karttaa etusivulle ');
  //<!-- Add Google Maps -->
  var myCenter = new google.maps.LatLng(61.5287, 23.63215);
  //console.log('karttaolio luotu ' );

  function initialize() {
    //console.log('initializessa ' );
    var mapProp = {
                   center:      myCenter,
                   zoom:        4,
                   scrollwheel: true,
                   draggable:   false,
                   mapTypeId:   google.maps.MapTypeId.ROADMAP
  };

    //console.log('mapProp ', mapProp );

    var map = new google.maps.Map(document.getElementById('mapPoint'),mapProp);

    var marker = new google.maps.Marker({
      position:myCenter,
    });

    marker.setMap(map);
  }

  //google.maps.event.addDomListener(window, 'load', initialize);
  //console.log('kutsutaan initializea ' );
  initialize();

}
