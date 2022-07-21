
var anyURL ;
var varWindow = null;
function popupNote( anyNotes ) {
	//
	var wdth = 550 , hght = 550 ;
	var popX = window.screenX + ( window.innerWidth /2 ) - ( wdth/2 );
	var popY = window.screenY + ( window.innerHeight/2 ) - ( hght/2 );
    var varName = new Date( ).toISOString();
	var varOpts = 'scrollbars=1, resizable=1, menubar=0, location=0, directories=0, toolbar=0, status=0' ;
	var varStrg = 'width=' + wdth + ' , height=' + hght + ' , top=' + popY + ' , left=' + popX + ' , ' + varOpts ;
	//
	if( !varWindow || varWindow.closed || typeof varWindow.closed=='undefined' ) { } else
	{ varWindow.self.close( ) ; }
	//
	varWindow = window.open( '' , 'popupNote' , varStrg , false ) ;
	let txtLines = '<html><head><title>MLG</title>'
	txtLines += '<link rel = "StyleSheet" href = "/basics.css" type = "text/css" />'
	txtLines += '<style>body { margin:0px; padding: 0px; border: 0px; }</style>'
	txtLines += '</head><body><center>'; // onmouseout = "window.self.close( )" onmouseover
	txtLines += anyNotes
	txtLines += '<br /><button onclick = "window.self.close( )">CLOSE</button>'
	txtLines += '</center></body</html>'
	//
	varWindow.document.writeln( txtLines ) ;
	varWindow.self.focus( ) ;
	return ;
 }

function showImage( varItem ) {
	//
	var varSrc = '';
	varSrc += '<b>' + varItem.src.substring( varItem.src.lastIndexOf( '/' ) + 1 ) + '</b><br />';
	varSrc += '<img style = "width: 500px; height: 500px; margin:0px; padding: 0px; border: 0px;" ' ;
	varSrc += 'src = "' + varItem.src + '" ' + 'onclick = "window.self.close( )" />';
	popupNote( varSrc );
}
