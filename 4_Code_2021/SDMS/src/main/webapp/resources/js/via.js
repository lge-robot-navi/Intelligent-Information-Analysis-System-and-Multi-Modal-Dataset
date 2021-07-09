var REGION_SHAPE = { SELECT:'select', RECT:'rect', POLYGON:'polygon'};

var VIA_REGION_EDGE_TOL           = 5;   // pixel
var VIA_REGION_POINT_RADIUS       = 3;
var VIA_POLYGON_VERTEX_MATCH_TOL  = 5;
var VIA_REGION_MIN_DIM            = 3;
var VIA_MOUSE_CLICK_TOL           = 2;
var VIA_POLYGON_RESIZE_VERTEX_OFFSET    = 100;
var VIA_CANVAS_DEFAULT_ZOOM_LEVEL_INDEX = 3;
var VIA_CANVAS_ZOOM_LEVELS = [0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0, 4, 5];

var VIA_THEME_REGION_BOUNDARY_WIDTH = 4;
var VIA_THEME_BOUNDARY_LINE_COLOR   = "#1a1a1a";
var VIA_THEME_BOUNDARY_FILL_COLOR   = "#aaeeff";
var VIA_THEME_SEL_REGION_FILL_COLOR = "#808080";
var VIA_THEME_SEL_REGION_FILL_BOUNDARY_COLOR = "#000000";
var VIA_THEME_SEL_REGION_OPACITY    = 0.5;
var VIA_THEME_MESSAGE_TIMEOUT_MS    = 2500;
var VIA_THEME_ATTRIBUTE_VALUE_FONT  = '10pt Sans';
var VIA_THEME_CONTROL_POINT_COLOR   = '#ff0000';

var VIA_CSV_SEP        = ',';
var VIA_CSV_KEYVAL_SEP = ':';

var _via_img_metadata = {};   // data structure to store loaded images metadata
var _via_img_count    = 0;    // count of the loaded images
var _via_canvas_regions = []; // image regions spec. in canvas space
var _via_canvas_scale   = 1.0;// current scale of canvas image

var _via_image_id_list  = []; // array of image id (in original order)
var _via_image_id       = ''; // id={filename+length} of current image
var _via_image_index    = -1; // index

var _via_current_image_filename;
var _via_current_image;
var _via_current_image_width;
var _via_current_image_height;

// image canvas
var _via_img_canvas = document.getElementById("image_canvas");
var _via_img_ctx    = _via_img_canvas.getContext("2d");
var _via_reg_canvas = document.getElementById("region_canvas");
var _via_reg_ctx    = _via_reg_canvas.getContext("2d");
var _via_canvas_width, _via_canvas_height;

// canvas zoom
var _via_canvas_zoom_level_index   = VIA_CANVAS_DEFAULT_ZOOM_LEVEL_INDEX; // 1.0
var _via_canvas_scale_without_zoom = 1.0;

// state of the application
var _via_is_user_drawing_region  = false;
var _via_current_image_loaded    = false;
var _via_is_window_resized       = false;
var _via_is_user_resizing_region = false;
var _via_is_user_moving_region   = false;
var _via_is_user_drawing_polygon = false;
var _via_is_region_selected      = false;
var _via_is_all_region_selected  = false;
var _via_is_user_updating_attribute_name  = false;
var _via_is_user_updating_attribute_value = false;
var _via_is_user_adding_attribute_name    = false;
var _via_is_attributes_panel_visible = false;
var _via_is_reg_attr_panel_visible   = false;
var _via_is_canvas_zoomed            = false;
var _via_is_loading_current_image    = false;
var _via_is_region_id_visible        = true;
var _via_is_region_boundary_visible  = true;
var _via_is_ctrl_pressed             = false;

// region
var _via_current_shape             = REGION_SHAPE.SELECT;
var _via_current_polygon_region_id = -1;
var _via_user_sel_region_id        = -1;
var _via_click_x0 = 0; var _via_click_y0 = 0;
var _via_click_x1 = 0; var _via_click_y1 = 0;
var _via_region_click_x, _via_region_click_y;
var _via_copied_image_regions = [];
var _via_region_edge          = [-1, -1];

// message
var _via_message_clear_timer;

// attributes
var _via_region_attributes             = new Set();

// persistence to local storage
var _via_is_local_storage_available = false;
var _via_is_save_ongoing = false;

// image list
var _via_reload_img_table = true;
var _via_loaded_img_fn_list = [];
var _via_loaded_img_region_attr_miss_count = [];
var _via_loaded_img_table_html = [];


// UI html elements
var invisible_file_input = document.getElementById("invisible_file_input");
var ui_top_panel = document.getElementById("ui_top_panel");
var canvas_panel = document.getElementById("canvas_panel");

var attributes_panel      = document.getElementById('attributes_panel');
var annotation_data_window;

//
// Data structure for annotations
//
function ImageMetadata(fileref, filename, size) {
    this.filename = filename;
    this.size     = size;
    this.fileref  = fileref;          // image url or local file ref.
    this.regions  = [];
    this.base64_img_data = '';        // image data stored as base 64
}

function ImageRegion() {
    this.is_user_selected  = false;
    this.shape_attributes  = new Map(); // region shape attributes
    this.region_attributes = new Map(); // region attributes
}

//
// Initialization routine
//
function _via_init() {
    _via_is_local_storage_available = check_local_storage();
    if (_via_is_local_storage_available) {
        if (is_via_data_in_localStorage()) {
        }
    }

    // run attached sub-modules (if any)
    if (typeof _via_load_submodules === 'function') {
        setTimeout(function() {
            _via_load_submodules();
        }, 100);
    }
}

//
// Handlers for top navigation bar
//
function sel_local_images() {
    // source: https://developer.mozilla.org/en-US/docs/Using_files_from_web_applications
    if (invisible_file_input) {
        invisible_file_input.accept   = '.jpg,.jpeg,.png,.bmp';
        invisible_file_input.onchange = store_local_img_ref;
        invisible_file_input.click();
    }
}
function load_region_data() {
    if (invisible_file_input) {
        invisible_file_input.accept='.json';
        invisible_file_input.onchange = import_annotations_from_file;
        invisible_file_input.click();
    }
}
function import_attributes() {
    if (_via_current_image_loaded) {
        if (invisible_file_input) {
            invisible_file_input.accept   = '.json';
            invisible_file_input.onchange = import_region_attributes_from_file;
            invisible_file_input.click();
        }
    } else {
        show_message("Please load some images first");
    }
}
function set_all_text_panel_display(style_display) {
    var tp = document.getElementsByClassName('text_panel');
    for ( var i = 0; i < tp.length; ++i ) {
        tp[i].style.display = style_display;
    }
}
function clear_image_display_area() {
    hide_all_canvas();
    set_all_text_panel_display('none');
}

var imageTaggingData;
function store_url_img_ref(file, object) {
    var user_selected_images = [file];
    imageTaggingData = object;

    // clear browser cache if user chooses to load new images
    localStorage.clear();

    var discarded_file_count = 0;
    for ( var i = 0; i < user_selected_images.length; ++i ) {
        var filetype = user_selected_images[i].type.substr(0, 5);
        if ( filetype === 'image' ) {
            var filename = user_selected_images[i].name;
            var size     = user_selected_images[i].size;
            _via_img_metadata['image'] = new ImageMetadata(user_selected_images[i],
                filename,
                size);
            _via_image_id_list.push('image');
            _via_img_count = 1;
            _via_reload_img_table = true;
        } else {
            discarded_file_count += 1;
        }
    }

    if ( _via_img_metadata ) {
        var status_msg = 'Loaded image.';
        if ( discarded_file_count ) {
            status_msg += ' ( Discarded ' + discarded_file_count + ' non-image files! )';
        }
        if(object.lastUpdateImageMagnification) {
            for( var i = 0; i < VIA_CANVAS_ZOOM_LEVELS.length; i++ ) {
                if( object.lastUpdateImageMagnification == VIA_CANVAS_ZOOM_LEVELS[i] ) {
                    _via_canvas_zoom_level_index = i;
                    break;
                }
            }
        } else {
            _via_canvas_zoom_level_index = VIA_CANVAS_DEFAULT_ZOOM_LEVEL_INDEX;
        }
        show_message(status_msg);
        show_image(0);
        _via_clear_reg_canvas();
        attributes_panel.style.display   = 'block';
        _via_is_attributes_panel_visible = false;
        _via_is_reg_attr_panel_visible   = false;
        _via_reg_canvas.focus();
        
//        console.log('>>>>>>>>>> imageJsonFileDesc: '+object.imageJsonFileDesc);
        console.log('>>>>>>>>>> sensorDataJsonFileDesc: '+object.sensorDataJsonFileDesc);
        console.log('>>>>>>>>>> sensorDataFileTypeCd: '+object.sensorDataFileTypeCd);
        
        /*
		(100) RGB = 1280 x 720 => 18, 372
		(200) Depth = 1280 x 720 => 23, 367
		(300) Night Vision1 = 720 x 480 => 99, 284
		(400) Night Vision2  = 720 x 480 => 85, 286
		(500) Thermal = 640 x 512 => 50, 236
		*/
        if(object.sensorDataJsonFileDesc) {
	        var sensorType = object.sensorDataFileTypeCd;
	        var d = JSON.parse(object.sensorDataJsonFileDesc);
	
	        for (var image_id in d) {
	            if ( _via_img_metadata.hasOwnProperty(image_id) ) {
	                var regions = d[image_id].regions;
	                for ( var i in regions ) {
	                	console.log("x",regions[i].shape_attributes.x);
	                	console.log("y",regions[i].shape_attributes.y);

	                	if (sensorType === "100") {
	                		regions[i].shape_attributes.x = regions[i].shape_attributes.x;
	                    	regions[i].shape_attributes.y = regions[i].shape_attributes.y;
	                	}else if (sensorType === "200") {
	                		regions[i].shape_attributes.x = regions[i].shape_attributes.x + 5;
	                    	regions[i].shape_attributes.y = regions[i].shape_attributes.y - 5;
	                	} else if (sensorType === "300") {
	                		regions[i].shape_attributes.x = regions[i].shape_attributes.x + 81;
	                    	regions[i].shape_attributes.y = regions[i].shape_attributes.y - 88;
	                	} else if (sensorType === "400") {
	                		regions[i].shape_attributes.x = regions[i].shape_attributes.x + 67;
	                    	regions[i].shape_attributes.y = regions[i].shape_attributes.y - 86;
	                	} else if (sensorType === "500") {
	                		regions[i].shape_attributes.x = regions[i].shape_attributes.x + 32;
	                    	regions[i].shape_attributes.y = regions[i].shape_attributes.y - 136;
	                	}
	                }
	            }
	        }
	        
//	        for (var image_id in d) {
//	            if ( _via_img_metadata.hasOwnProperty(image_id) ) {
//	                var regions = d[image_id].regions;
//	                for ( var i in regions ) {
//	                	console.log("x",regions[i].shape_attributes.x);
//	                	console.log("y",regions[i].shape_attributes.y);
//	                }
//	            }
//	        }
        }
        

//        if(object.imageJsonFileDesc) {
//            import_annotations_from_json(object.imageJsonFileDesc);
//        }
        
        if(object.sensorDataJsonFileDesc) {
        	import_annotations_from_json(object.sensorDataJsonFileDesc);
        }


    } else {
        show_message("Please upload some image files!");
    }
}

//
// Local file uploaders
//
function store_local_img_ref(event) {
    var user_selected_images = event.target.files;
    var original_image_count = _via_img_count;

    // clear browser cache if user chooses to load new images
    localStorage.clear();

    var discarded_file_count = 0;
    for ( var i = 0; i < user_selected_images.length; ++i ) {
        var filetype = user_selected_images[i].type.substr(0, 5);
        if ( filetype === 'image' ) {
            var filename = user_selected_images[i].name;
            var size     = user_selected_images[i].size;
            var img_id   = _via_get_image_id(filename, size);

            if ( _via_img_metadata.hasOwnProperty(img_id) ) {
                if ( _via_img_metadata[img_id].fileref ) {
                    show_message('Image ' + filename + ' already loaded. Skipping!');
                } else {
                    _via_img_metadata[img_id].fileref = user_selected_images[i];
                    show_message('Regions already exist for file ' + filename + ' !');
                }
            } else {
                _via_img_metadata['image'] = new ImageMetadata(user_selected_images[i],
                    filename,
                    size);
                _via_image_id_list.push('image');
                _via_img_count = 1;
                _via_reload_img_table = true;
            }
        } else {
            discarded_file_count += 1;
        }
    }

    if ( _via_img_metadata ) {
        var status_msg = 'Loaded images.';
        if ( discarded_file_count ) {
            status_msg += ' ( Discarded ' + discarded_file_count + ' non-image files! )';
        }
        show_message(status_msg);

        if ( _via_image_index === -1 ) {
            show_image(0);
        } else {
            show_image( original_image_count );
        }

        _via_clear_reg_canvas();
        attributes_panel.style.display   = 'none';
        _via_is_attributes_panel_visible = false;
        _via_is_reg_attr_panel_visible   = false;
        _via_reg_canvas.focus();
    } else {
        show_message("Please upload some image files!");
    }
}

//
// Data Importer
//

function import_region_attributes_from_file(event) {
    var selected_files = event.target.files;
    for ( var i=0 ; i < selected_files.length; ++i ) {
        var file = selected_files[i];
        switch(file.type) {
            case 'text/csv':
                load_text_file(file, import_region_attributes_from_csv);
                break;
            default:
                show_message('Region attributes cannot be imported from file of type ' + file.type);
        }
    }
}

function import_region_attributes_from_csv(data) {
    data = data.replace(/\n/g, ''); // discard newline \n
    var csvdata = data.split(',');
    var attributes_import_count = 0;
    for ( var i = 0; i < csvdata.length; ++i ) {
        if ( !_via_region_attributes.has(csvdata[i]) ) {
            _via_region_attributes.add(csvdata[i]);
            attributes_import_count += 1;
        }
    }

    _via_reload_img_table = true;
    show_message('Imported ' + attributes_import_count + ' attributes from CSV file');
    save_current_data_to_browser_cache();
}

function import_annotations_from_file(event) {
    var selected_files = event.target.files;
    for ( var i = 0; i < selected_files.length; ++i ) {
        var file = selected_files[i];
        switch(file.type) {
            case '': // Windows 10: Firefox and Chrome do not report filetype
                show_message('File type for ' + file.name + ' cannot be determined! Assuming text/plain.');
                break;

            case 'text/json':
            case 'application/json':
                load_text_file(file, import_annotations_from_json);
                break;

            default:
                show_message('Annotations cannot be imported from file of type ' + file.type);
        }
    }
}

function import_annotations_from_json(data) {
    if (data === '' || typeof(data) === 'undefined') {
        return;
    }

    var d = JSON.parse(data);

    var region_import_count = 0;
    for (var image_id in d) {
        if ( _via_img_metadata.hasOwnProperty(image_id) ) {

            // copy regions
            var regions = d[image_id].regions;
            for ( var i in regions ) {
                var regioni = new ImageRegion();
                for ( var key in regions[i].shape_attributes ) {
                    regioni.shape_attributes.set(key, regions[i].shape_attributes[key]);
                }
                for ( var key in regions[i].region_attributes ) {
                    regioni.region_attributes.set(key, regions[i].region_attributes[key]);

                    if ( !_via_region_attributes.has(key) ) {
                        _via_region_attributes.add(key);
                    }
                }

                // add regions only if they are present
                if ( regioni.shape_attributes.size > 0 ||
                    regioni.region_attributes.size > 0 ) {
                    _via_img_metadata[image_id].regions.push(regioni);
                    region_import_count += 1;
                }
            }
        }
    }
    // show_message('Import Summary : [' + region_import_count + '] regions');

    _via_reload_img_table = true;
    show_image(_via_image_index);
}

// s = '{"name":"rect","x":188,"y":90,"width":243,"height":233}'
function json_str_to_map(s) {
    if (typeof(s) === 'undefined' || s.length === 0 ) {
        return new Map();
    }

    var d = JSON.parse(s);
    var m = new Map();
    for ( var key in d ) {
        m.set(key, d[key]);
    }
    return m;
}

// ensure the exported json string conforms to RFC 4180
// see: https://en.wikipedia.org/wiki/Comma-separated_values
function map_to_json(m) {
    var s = [];
    for ( var key of m.keys() ) {
        var v   = m.get(key);
        var si  = JSON.stringify(key);
        si += VIA_CSV_KEYVAL_SEP;
        si += JSON.stringify(v);
        s.push( si );
    }
    return '{' + s.join(VIA_CSV_SEP) + '}';
}

function clone_image_region(r0) {
    var r1 = new ImageRegion();
    r1.is_user_selected = r0.is_user_selected;

    // copy shape attributes
    for ( var key of r0.shape_attributes.keys() ) {
        var value = r0.shape_attributes.get(key);
        r1.shape_attributes.set(key, value);
    }

    // copy region attributes
    for ( var key of r0.region_attributes.keys() ) {
        var value = r0.region_attributes.get(key);
        r1.region_attributes.set(key, value);
    }
    return r1;
}

function _via_get_image_id(filename, size) {
    if ( typeof(size) === 'undefined' ) {
        return filename;
    } else {
        return filename + size;
    }
}

function load_text_file(text_file, callback_function) {
    if (!text_file) {
        return;
    } else {
        var text_reader = new FileReader();
        text_reader.addEventListener( 'progress', function(e) {
            show_message('Loading data from text file : ' + text_file.name + ' ... ');
        }, false);

        text_reader.addEventListener( 'error', function() {
            show_message('Error loading data from text file :  ' + text_file.name + ' !');
            callback_function('');
        }, false);

        text_reader.addEventListener( 'load', function() {
            callback_function(text_reader.result);
        }, false);
        text_reader.readAsText(text_file, 'utf-8');
    }
}

function get_save_data() {
    imageTaggingData.imageJsonFileDesc = pack_via_metadata()[0];
    imageTaggingData.lastUpdateImageMagnification = VIA_CANVAS_ZOOM_LEVELS[_via_canvas_zoom_level_index];
    return imageTaggingData;
}

//
// Data Exporter
//
function pack_via_metadata() {
    // JSON.stringify() does not work with Map()
    // hence, we cast everything as objects
    var _via_img_metadata_as_obj = {};
    for ( var image_id in _via_img_metadata ) {
        var image_data = {};
        //image_data.fileref = _via_img_metadata[image_id].fileref;
        image_data.fileref = '';
        image_data.size = _via_img_metadata[image_id].size;
        image_data.filename = _via_img_metadata[image_id].filename;
        image_data.base64_img_data = '';
        //image_data.base64_img_data = _via_img_metadata[image_id].base64_img_data;

        // copy all region shape_attributes
        image_data.regions = {};
        for ( var i = 0; i < _via_img_metadata[image_id].regions.length; ++i ) {
            image_data.regions[i] = {};
            image_data.regions[i].shape_attributes = {};
            image_data.regions[i].region_attributes = {};
            // copy region shape_attributes
            for ( var key of _via_img_metadata[image_id].regions[i].shape_attributes.keys()) {
                var value = _via_img_metadata[image_id].regions[i].shape_attributes.get(key);
                image_data.regions[i].shape_attributes[key] = value;
            }
            // copy region_attributes
            for ( var key of _via_img_metadata[image_id].regions[i].region_attributes.keys()) {
                var value = _via_img_metadata[image_id].regions[i].region_attributes.get(key);
                image_data.regions[i].region_attributes[key] = value;
            }
        }
        _via_img_metadata_as_obj[image_id] = image_data;
    }
    return [JSON.stringify(_via_img_metadata_as_obj)];
}

function save_data_to_local_file(data, filename) {
    var a      = document.createElement('a');
    a.href     = URL.createObjectURL(data);
    a.target   = '_blank';
    a.download = filename;

    // simulate a mouse click event
    var event = new MouseEvent('click', {
        view: window,
        bubbles: true,
        cancelable: true
    });

    a.dispatchEvent(event);
}

//
// Maintainers of user interface
//

function show_message(msg, t) {
    if ( _via_message_clear_timer ) {
        clearTimeout(_via_message_clear_timer); // stop any previous timeouts
    }
    var timeout = t;
    if ( typeof t === 'undefined' ) {
        timeout = VIA_THEME_MESSAGE_TIMEOUT_MS;
    }
    document.getElementById('message_panel').innerHTML = msg;
    _via_message_clear_timer = setTimeout( function() {
        document.getElementById('message_panel').innerHTML = ' ';
    }, timeout);
}

function show_image(image_index) {
    var img_id = _via_image_id_list[image_index];
    if ( !_via_img_metadata.hasOwnProperty(img_id)) {
        return;
    }

    var img_filename = _via_img_metadata[img_id].filename;
    var img_reader = new FileReader();
    _via_is_loading_current_image = true;

    img_reader.addEventListener( "loadstart", function(e) {
    }, false);

    img_reader.addEventListener( "progress", function(e) {
    }, false);

    img_reader.addEventListener( "error", function() {
        _via_is_loading_current_image = false;
        show_message("Error loading image " + img_filename + " !");
    }, false);

    img_reader.addEventListener( "abort", function() {
        _via_is_loading_current_image = false;
        show_message("Aborted loading image " + img_filename + " !");
    }, false);

    img_reader.addEventListener( "load", function() {
        _via_current_image = new Image();

        _via_current_image.addEventListener( "error", function() {
            _via_is_loading_current_image = false;
            show_message("Error loading image " + img_filename + " !");
        }, false);

        _via_current_image.addEventListener( "abort", function() {
            _via_is_loading_current_image = false;
            show_message("Aborted loading image " + img_filename + " !");
        }, false);

        _via_current_image.addEventListener( "load", function() {

            // update the current state of application
            _via_image_id = img_id;
            _via_image_index = image_index;
            _via_current_image_filename = img_filename;
            _via_current_image_loaded = true;
            _via_is_loading_current_image = false;
            _via_click_x0 = 0; _via_click_y0 = 0;
            _via_click_x1 = 0; _via_click_y1 = 0;
            _via_is_user_drawing_region = false;
            _via_is_window_resized = false;
            _via_is_user_resizing_region = false;
            _via_is_user_moving_region = false;
            _via_is_user_drawing_polygon = false;
            _via_is_region_selected = false;
            _via_user_sel_region_id = -1;
            _via_current_image_width = _via_current_image.naturalWidth;
            _via_current_image_height = _via_current_image.naturalHeight;

            // set the size of canvas
            // based on the current dimension of browser window
            var de = document.documentElement;
            // canvas_panel_width = de.clientWidth - 500;
            canvas_panel_width = $('#display_area').width() - 20;
            // canvas_panel_height = de.clientHeight - 2 * ui_top_panel.offsetHeight;
            // canvas_panel_height = de.clientHeight - 450;
            canvas_panel_height = $('#display_area').height() - 20;
            _via_canvas_width = _via_current_image_width;
            _via_canvas_height = _via_current_image_height;
            var scale_width, scale_height;
            if ( _via_canvas_width > canvas_panel_width ) {
                // resize image to match the panel width
                scale_width = canvas_panel_width / _via_current_image.naturalWidth;
                _via_canvas_width = canvas_panel_width;
                _via_canvas_height = _via_current_image.naturalHeight * scale_width;
            }
            if ( _via_canvas_height > canvas_panel_height ) {
                // resize further image if its height is larger than the image panel
                scale_height = canvas_panel_height / _via_canvas_height;
                _via_canvas_height = canvas_panel_height;
                _via_canvas_width = _via_canvas_width * scale_height;
            }
            _via_canvas_width = Math.round(_via_canvas_width);
            _via_canvas_height = Math.round(_via_canvas_height);
            _via_canvas_scale = _via_current_image.naturalWidth / _via_canvas_width;
            _via_canvas_scale_without_zoom = _via_canvas_scale;
            set_all_canvas_size(_via_canvas_width, _via_canvas_height);
            set_all_canvas_scale(_via_canvas_scale_without_zoom);

            // ensure that all the canvas are visible
            clear_image_display_area();
            show_all_canvas();

            // we only need to draw the image once in the image_canvas
            _via_img_ctx.clearRect(0, 0, _via_canvas_width, _via_canvas_height);
            _via_img_ctx.drawImage(_via_current_image, 0, 0,
                _via_canvas_width, _via_canvas_height);

            // refresh the attributes panel
            // kjcho
            // update_attributes_panel();

            // kjcho
            // $('#attributes_panel_table') 초기화 && hide
            // attributes_panel_meta_table input value change

            _via_load_canvas_regions(); // image to canvas space transform
            _via_redraw_reg_canvas();
            _via_reg_canvas.focus();

            // update the UI components to reflect newly loaded image
            // refresh the image list
            // @todo: let the height of image list match that of window
            _via_reload_img_table = true;
            update_attributes_panel();
            set_zoom();
        });
        _via_current_image.src = img_reader.result;
    }, false);

    if (_via_img_metadata[img_id].base64_img_data === '') {
        // load image from file
        img_reader.readAsDataURL( _via_img_metadata[img_id].fileref );
    } else {
        // load image from base64 data or URL
        img_reader.readAsText( new Blob([_via_img_metadata[img_id].base64_img_data]) );
    }
}

// transform regions in image space to canvas space
function _via_load_canvas_regions() {
    // load all existing annotations into _via_canvas_regions
    var regions = _via_img_metadata['image'].regions;
    _via_canvas_regions  = [];
    for ( var i = 0; i < regions.length; ++i ) {
        var regioni = new ImageRegion();
        for ( var key of regions[i].shape_attributes.keys() ) {
            var value = regions[i].shape_attributes.get(key);
            regioni.shape_attributes.set(key, value);
        }
        _via_canvas_regions.push(regioni);

        switch(_via_canvas_regions[i].shape_attributes.get('name')) {
            case REGION_SHAPE.RECT:
                var x      = regions[i].shape_attributes.get('x') / _via_canvas_scale;
                var y      = regions[i].shape_attributes.get('y') / _via_canvas_scale;
                var width  = regions[i].shape_attributes.get('width')  / _via_canvas_scale;
                var height = regions[i].shape_attributes.get('height') / _via_canvas_scale;

                _via_canvas_regions[i].shape_attributes.set('x', Math.round(x));
                _via_canvas_regions[i].shape_attributes.set('y', Math.round(y));
                _via_canvas_regions[i].shape_attributes.set('width' , Math.round(width) );
                _via_canvas_regions[i].shape_attributes.set('height', Math.round(height));
                break;

            case REGION_SHAPE.POLYGON:
                var all_points_x = regions[i].shape_attributes.get('all_points_x').slice(0);
                var all_points_y = regions[i].shape_attributes.get('all_points_y').slice(0);
                for (var j=0; j<all_points_x.length; ++j) {
                    all_points_x[j] = Math.round(all_points_x[j] / _via_canvas_scale);
                    all_points_y[j] = Math.round(all_points_y[j] / _via_canvas_scale);
                }
                _via_canvas_regions[i].shape_attributes.set('all_points_x', all_points_x);
                _via_canvas_regions[i].shape_attributes.set('all_points_y', all_points_y);
                break;

        }
    }
}

// updates currently selected region shape
function select_region_shape(sel_shape_name) {
    for ( var shape_name in REGION_SHAPE ) {
        /*var ui_element = document.getElementById('region_shape_' + REGION_SHAPE[shape_name]);
        ui_element.classList.remove('selected');*/
        var _img = $('#region_shape_' + REGION_SHAPE[shape_name] + ' img');
        _img.attr('src', _img.attr('src').replace('on.', 'off.'));
    }

    _via_current_shape = sel_shape_name;
    /*var ui_element = document.getElementById('region_shape_' + _via_current_shape);
    ui_element.classList.add('selected');*/
    var _img = $('#region_shape_' + _via_current_shape + ' img');
    _img.attr('src', _img.attr('src').replace('off.', 'on.'));

    switch(_via_current_shape) {
        case REGION_SHAPE.RECT:
            show_message('Press single click and drag mouse to draw ' +
                _via_current_shape + ' region');
            break;

        case REGION_SHAPE.POLYGON:
            _via_is_user_drawing_polygon = false;
            _via_current_polygon_region_id = -1;

            show_message('Press single click to define polygon vertices and ' +
                'click first vertex to close path');
            break;

        case REGION_SHAPE.SELECT:
            show_message('Selection mode!');
            break;

        default:
            show_message('Unknown shape selected!');
    }
}

function set_all_canvas_size(w, h) {
    _via_img_canvas.height = h;
    _via_img_canvas.width  = w;

    _via_reg_canvas.height = h;
    _via_reg_canvas.width = w;

    canvas_panel.style.height = h + 'px';
    canvas_panel.style.width  = w + 'px';
}

function set_all_canvas_scale(s) {
    _via_img_ctx.scale(s, s);
    _via_reg_ctx.scale(s, s);
}

function show_all_canvas() {
    canvas_panel.style.display = 'inline-block';
}

function hide_all_canvas() {
    canvas_panel.style.display = 'none';
}

function reload_img_table() {
    _via_loaded_img_fn_list = [];
    _via_loaded_img_region_attr_miss_count = [];

    for ( var i=0; i < _via_img_count; ++i ) {
        img_id = _via_image_id_list[i];
        _via_loaded_img_fn_list[i] = _via_img_metadata[img_id].filename;
        _via_loaded_img_region_attr_miss_count[i] = count_missing_region_attr(img_id);
    }

    _via_loaded_img_table_html = [];
    _via_loaded_img_table_html.push('<ul>');
    for ( var i=0; i < _via_img_count; ++i ) {
        var fni = '';
        if ( i === _via_image_index ) {
            // highlight the current entry
            fni += '<li id="flist'+i+'" style="cursor: default;" title="' + _via_loaded_img_fn_list[i] + '">';
            fni += '<b>[' + (i+1) + '] ' + _via_loaded_img_fn_list[i] + '</b>';
        } else {
            fni += '<li id="flist'+i+'" onclick="jump_to_image(' + (i) + ')" title="' + _via_loaded_img_fn_list[i] + '">';
            fni += '[' + (i+1) + '] ' + _via_loaded_img_fn_list[i];
        }

        if ( _via_loaded_img_region_attr_miss_count[i] ) {
            fni += ' (' + '<span style="color: red;">';
            fni += _via_loaded_img_region_attr_miss_count[i] + '</span>' + ')';
        }

        fni += '</li>';
        _via_loaded_img_table_html.push(fni);
    }
    _via_loaded_img_table_html.push('</ul>');
}

function jump_to_image(image_index) {
    if ( _via_img_count <= 0 ) {
        return;
    }

    // reset zoom
    if ( _via_is_canvas_zoomed ) {
        _via_is_canvas_zoomed = false;
        _via_canvas_zoom_level_index = VIA_CANVAS_DEFAULT_ZOOM_LEVEL_INDEX;
        var zoom_scale = VIA_CANVAS_ZOOM_LEVELS[_via_canvas_zoom_level_index];
        set_all_canvas_scale(zoom_scale);
        set_all_canvas_size(_via_canvas_width, _via_canvas_height);
        _via_canvas_scale = _via_canvas_scale_without_zoom;
    }

    if ( image_index >= 0 && image_index < _via_img_count) {
        show_image(image_index);
    }
}

function count_missing_region_attr(img_id) {
    var miss_region_attr_count = 0;
    var attr_count = _via_region_attributes.size;
    for( var i=0; i < _via_img_metadata[img_id].regions.length; ++i ) {
        var set_attr_count = _via_img_metadata[img_id].regions[i].region_attributes.size;
        miss_region_attr_count += ( attr_count - set_attr_count );
    }
    return miss_region_attr_count;
}

function toggle_all_regions_selection(is_selected) {
    for (var i=0; i<_via_canvas_regions.length; ++i) {
        _via_canvas_regions[i].is_user_selected = is_selected;
        _via_img_metadata['image'].regions[i].is_user_selected = is_selected;
    }
    _via_is_all_region_selected = is_selected;
}
function select_only_region(region_id) {
    toggle_all_regions_selection(false);
    set_region_select_state(region_id, true);
    _via_is_region_selected = true;
    _via_user_sel_region_id = region_id;
}
function set_region_select_state(region_id, is_selected) {
    _via_canvas_regions[region_id].is_user_selected = is_selected;
    _via_img_metadata['image'].regions[region_id].is_user_selected = is_selected;
}
function show_annotation_data() {
    var hstr = pack_via_metadata().join('');
    var window_features = 'toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes,status=no';
    window_features += ',width=800,height=600';
    annotation_data_window = window.open('', 'Image Metadata ', window_features);
    annotation_data_window.document.body.innerHTML = hstr;
}

//
// Image click handlers
//

// enter annotation mode on double click
/*_via_reg_canvas.addEventListener('dblclick', function(e) {
    _via_click_x0 = e.offsetX; _via_click_y0 = e.offsetY;
    var region_id = is_inside_region(_via_click_x0, _via_click_y0);

    if (region_id !== -1) {
        // user clicked inside a region, show attribute panel
        if(!_via_is_reg_attr_panel_visible) {
            toggle_reg_attr_panel();
        }
    }

}, false);*/

// user clicks on the canvas
_via_reg_canvas.addEventListener('mousedown', function(e) {
    _via_click_x0 = e.offsetX; _via_click_y0 = e.offsetY;
    _via_region_edge = is_on_region_corner(_via_click_x0, _via_click_y0);
    var region_id = is_inside_region(_via_click_x0, _via_click_y0);

    if ( _via_is_region_selected ) {
        // check if user clicked on the region boundary
        if ( _via_region_edge[1] > 0 ) {
            if ( !_via_is_user_resizing_region ) {
                // resize region
                if ( _via_region_edge[0] !== _via_user_sel_region_id ) {
                    _via_user_sel_region_id = _via_region_edge[0];
                }
                _via_is_user_resizing_region = true;
            }
        } else {
            var yes = is_inside_this_region(_via_click_x0,
                _via_click_y0,
                _via_user_sel_region_id);
            if (yes) {
                if( !_via_is_user_moving_region ) {
                    _via_is_user_moving_region = true;
                    _via_region_click_x = _via_click_x0;
                    _via_region_click_y = _via_click_y0;
                }
            }
            if ( region_id === -1 ) {
                // mousedown on outside any region
                _via_is_user_drawing_region = true;
                // unselect all regions
                _via_is_region_selected = false;
                _via_user_sel_region_id = -1;
                toggle_all_regions_selection(false);
            }
        }
    } else {
        if ( region_id === -1 ) {
            // mousedown outside a region
            if (_via_current_shape !== REGION_SHAPE.POLYGON) {
                // this is a bounding box drawing event
                _via_is_user_drawing_region = true;
            }
        } else {
            // mousedown inside a region
            // this could lead to (1) region selection or (2) region drawing
            _via_is_user_drawing_region = true;
        }
    }
    e.preventDefault();
}, false);

// implements the following functionalities:
//  - new region drawing (including polygon)
//  - moving/resizing/select/unselect existing region
_via_reg_canvas.addEventListener('mouseup', function(e) {
    _via_click_x1 = e.offsetX; _via_click_y1 = e.offsetY;

    var click_dx = Math.abs(_via_click_x1 - _via_click_x0);
    var click_dy = Math.abs(_via_click_y1 - _via_click_y0);

    // indicates that user has finished moving a region
    /* 태깅 도형 이동 */
    if ( _via_is_user_moving_region ) {
        _via_is_user_moving_region = false;
        _via_reg_canvas.style.cursor = "default";

        var move_x = Math.round(_via_click_x1 - _via_region_click_x);
        var move_y = Math.round(_via_click_y1 - _via_region_click_y);

        if (Math.abs(move_x) > VIA_MOUSE_CLICK_TOL ||
            Math.abs(move_y) > VIA_MOUSE_CLICK_TOL) {

            var image_attr = _via_img_metadata['image'].regions[_via_user_sel_region_id].shape_attributes;
            var canvas_attr = _via_canvas_regions[_via_user_sel_region_id].shape_attributes;

            switch( canvas_attr.get('name') ) {
                case REGION_SHAPE.RECT:
                    var xnew = image_attr.get('x') + Math.round(move_x * _via_canvas_scale);
                    var ynew = image_attr.get('y') + Math.round(move_y * _via_canvas_scale);
                    image_attr.set('x', xnew);
                    image_attr.set('y', ynew);

                    canvas_attr.set('x', Math.round( image_attr.get('x') / _via_canvas_scale) );
                    canvas_attr.set('y', Math.round( image_attr.get('y') / _via_canvas_scale) );
                    break;
                case REGION_SHAPE.POLYGON:
                    var img_px = image_attr.get('all_points_x');
                    var img_py = image_attr.get('all_points_y');
                    for (var i=0; i<img_px.length; ++i) {
                        img_px[i] = img_px[i] + Math.round(move_x * _via_canvas_scale);
                        img_py[i] = img_py[i] + Math.round(move_y * _via_canvas_scale);
                    }

                    var canvas_px = canvas_attr.get('all_points_x');
                    var canvas_py = canvas_attr.get('all_points_y');
                    for (var i=0; i<canvas_px.length; ++i) {
                        canvas_px[i] = Math.round( img_px[i] / _via_canvas_scale );
                        canvas_py[i] = Math.round( img_py[i] / _via_canvas_scale );
                    }
                    break;
            }
        } else {
            // indicates a user click on an already selected region
            // this could indicate a user's intention to select another
            // nested region within this region

            // traverse the canvas regions in alternating ascending
            // and descending order to solve the issue of nested regions
            var nested_region_id = is_inside_region(_via_click_x0, _via_click_y0, true);
            if (nested_region_id >= 0 &&
                nested_region_id !== _via_user_sel_region_id) {
                _via_user_sel_region_id = nested_region_id;
                _via_is_region_selected = true;
                _via_is_user_moving_region = false;

                // de-select all other regions if the user has not pressed Shift
                if ( !e.shiftKey ) {
                    toggle_all_regions_selection(false);
                }
                set_region_select_state(nested_region_id, true);
            }
        }
        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
        save_current_data_to_browser_cache();
        update_attributes_panel();
        return;
    }

    // indicates that user has finished resizing a region
    // 태깅 도형 리사이징
    if ( _via_is_user_resizing_region ) {
        // _via_click(x0,y0) to _via_click(x1,y1)
        _via_is_user_resizing_region = false;
        _via_reg_canvas.style.cursor = "default";

        // update the region
        var region_id = _via_region_edge[0];
        var image_attr = _via_img_metadata['image'].regions[region_id].shape_attributes;
        var canvas_attr = _via_canvas_regions[region_id].shape_attributes;

        switch (canvas_attr.get('name')) {
            case REGION_SHAPE.RECT:
                var d = [canvas_attr.get('x'), canvas_attr.get('y'), 0, 0];
                d[2] = d[0] + canvas_attr.get('width');
                d[3] = d[1] + canvas_attr.get('height');

                var mx = _via_current_x;
                var my = _via_current_y;
                var preserve_aspect_ratio = false;

                // constrain (mx,my) to lie on a line connecting a diagonal of rectangle
                if ( _via_is_ctrl_pressed ) {
                    preserve_aspect_ratio = true;
                }

                rect_update_corner(_via_region_edge[1], d, mx, my, preserve_aspect_ratio);
                rect_standarize_coordinates(d);

                var w = Math.abs(d[2] - d[0]);
                var h = Math.abs(d[3] - d[1]);

                image_attr.set('x', Math.round(d[0] * _via_canvas_scale));
                image_attr.set('y', Math.round(d[1] * _via_canvas_scale));
                image_attr.set('width', Math.round(w * _via_canvas_scale));
                image_attr.set('height', Math.round(h * _via_canvas_scale));

                canvas_attr.set('x', Math.round( image_attr.get('x') / _via_canvas_scale) );
                canvas_attr.set('y', Math.round( image_attr.get('y') / _via_canvas_scale) );
                canvas_attr.set('width', Math.round( image_attr.get('width') / _via_canvas_scale) );
                canvas_attr.set('height', Math.round( image_attr.get('height') / _via_canvas_scale) );
                break;

            case REGION_SHAPE.POLYGON:
                var moved_vertex_id = _via_region_edge[1] - VIA_POLYGON_RESIZE_VERTEX_OFFSET;

                var imx = Math.round(_via_current_x * _via_canvas_scale);
                var imy = Math.round(_via_current_y * _via_canvas_scale);
                image_attr.get('all_points_x')[moved_vertex_id] = imx;
                image_attr.get('all_points_y')[moved_vertex_id] = imy;
                canvas_attr.get('all_points_x')[moved_vertex_id] = Math.round( imx / _via_canvas_scale );
                canvas_attr.get('all_points_y')[moved_vertex_id] = Math.round( imy / _via_canvas_scale );

                if (moved_vertex_id === 0) {
                    // move both first and last vertex because we
                    // the initial point at the end to close path
                    var n = canvas_attr.get('all_points_x').length;
                    image_attr.get('all_points_x')[n-1] = imx;
                    image_attr.get('all_points_y')[n-1] = imy;
                    canvas_attr.get('all_points_x')[n-1] = Math.round( imx / _via_canvas_scale );;
                    canvas_attr.get('all_points_y')[n-1] = Math.round( imy / _via_canvas_scale );;
                }
                break;
        }

        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
        save_current_data_to_browser_cache();
        update_attributes_panel();
        return;
    }

    // denotes a single click (= mouse down + mouse up)
    if ( click_dx < VIA_MOUSE_CLICK_TOL ||
        click_dy < VIA_MOUSE_CLICK_TOL ) {
        // if user is already drawing ploygon, then each click adds a new point
        // 폴리곤 그리기
        if ( _via_is_user_drawing_polygon ) {
            var canvas_x0 = Math.round(_via_click_x0);
            var canvas_y0 = Math.round(_via_click_y0);

            // check if the clicked point is close to the first point
            var fx0 = _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.get('all_points_x')[0];
            var fy0 = _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.get('all_points_y')[0];
            var  dx = (fx0 - canvas_x0);
            var  dy = (fy0 - canvas_y0);
            if ( Math.sqrt(dx*dx + dy*dy) <= VIA_POLYGON_VERTEX_MATCH_TOL ) {
                // user clicked on the first polygon point to close the path
                _via_is_user_drawing_polygon = false;

                // add all polygon points stored in _via_canvas_regions[]
                var all_points_x = _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.get('all_points_x').slice(0);
                var all_points_y = _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.get('all_points_y').slice(0);
                // close path
                all_points_x.push(all_points_x[0]);
                all_points_y.push(all_points_y[0]);

                var canvas_all_points_x = [];
                var canvas_all_points_y = [];

                var points_str = '';
                for ( var i=0; i<all_points_x.length; ++i ) {
                    all_points_x[i] = Math.round( all_points_x[i] * _via_canvas_scale );
                    all_points_y[i] = Math.round( all_points_y[i] * _via_canvas_scale );

                    canvas_all_points_x[i] = Math.round( all_points_x[i] / _via_canvas_scale );
                    canvas_all_points_y[i] = Math.round( all_points_y[i] / _via_canvas_scale );

                    points_str += all_points_x[i] + ' ' + all_points_y[i] + ',';
                }
                points_str = points_str.substring(0, points_str.length-1); // remove last comma

                var polygon_region = new ImageRegion();
                polygon_region.shape_attributes.set('name', 'polygon');
                //polygon_region.shape_attributes.set('points', points_str);
                polygon_region.shape_attributes.set('all_points_x', all_points_x);
                polygon_region.shape_attributes.set('all_points_y', all_points_y);
                _via_current_polygon_region_id = _via_img_metadata['image'].regions.length;
                _via_img_metadata['image'].regions.push(polygon_region);

                // update canvas
                _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.set('all_points_x', canvas_all_points_x);
                _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.set('all_points_y', canvas_all_points_y);

                // newly drawn region is automatically selected
                select_only_region(_via_current_polygon_region_id);

                _via_current_polygon_region_id = -1;
                update_attributes_panel();
                save_current_data_to_browser_cache();
            } else {
                // user clicked on a new polygon point
                _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.get('all_points_x').push(canvas_x0);
                _via_canvas_regions[_via_current_polygon_region_id].shape_attributes.get('all_points_y').push(canvas_y0);
            }
        } else {
            var region_id = is_inside_region(_via_click_x0, _via_click_y0);
            if ( region_id >= 0 ) {
                // first click selects region
                _via_user_sel_region_id     = region_id;
                _via_is_region_selected     = true;
                _via_is_user_moving_region  = false;
                _via_is_user_drawing_region = false;

                // de-select all other regions if the user has not pressed Shift
                if ( !e.shiftKey ) {
                    toggle_all_regions_selection(false);
                }
                set_region_select_state(region_id, true);
                update_attributes_panel();
                //show_message('Click and drag to move or resize the selected region');
            } else {
                if ( _via_is_user_drawing_region ) {
                    // clear all region selection
                    _via_is_user_drawing_region = false;
                    _via_is_region_selected     = false;
                    _via_use_sel_region_id      = -1;
                    toggle_all_regions_selection(false);

                    update_attributes_panel();
                } else {
                    switch (_via_current_shape) {
                        case REGION_SHAPE.POLYGON:
                            // user has clicked on the first point in a new polygon
                            _via_is_user_drawing_polygon = true;

                            var canvas_polygon_region = new ImageRegion();
                            canvas_polygon_region.shape_attributes.set('name', REGION_SHAPE.POLYGON);
                            canvas_polygon_region.shape_attributes.set('all_points_x', [Math.round(_via_click_x0)]);
                            canvas_polygon_region.shape_attributes.set('all_points_y', [Math.round(_via_click_y0)]);
                            _via_canvas_regions.push(canvas_polygon_region);
                            _via_current_polygon_region_id =_via_canvas_regions.length - 1;
                            break;
                    }
                }
            }
        }
        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
        return;
    }

    // indicates that user has finished drawing a new region
    // 도형 그리기
    if ( _via_is_user_drawing_region ) {

        _via_is_user_drawing_region = false;

        if (_via_current_shape === REGION_SHAPE.SELECT) {
            return;
        }

        var region_x0, region_y0, region_x1, region_y1;
        // ensure that (x0,y0) is top-left and (x1,y1) is bottom-right
        if ( _via_click_x0 < _via_click_x1 ) {
            region_x0 = _via_click_x0;
            region_x1 = _via_click_x1;
        } else {
            region_x0 = _via_click_x1;
            region_x1 = _via_click_x0;
        }

        if ( _via_click_y0 < _via_click_y1 ) {
            region_y0 = _via_click_y0;
            region_y1 = _via_click_y1;
        } else {
            region_y0 = _via_click_y1;
            region_y1 = _via_click_y0;
        }

        var original_img_region = new ImageRegion();
        var canvas_img_region = new ImageRegion();
        var region_dx = Math.abs(region_x1 - region_x0);
        var region_dy = Math.abs(region_y1 - region_y0);

        // newly drawn region is automatically selected
        toggle_all_regions_selection(false);
        original_img_region.is_user_selected = true;
        canvas_img_region.is_user_selected = true;
        _via_is_region_selected = true;
        _via_user_sel_region_id = _via_canvas_regions.length; // new region's id

        if ( region_dx > VIA_REGION_MIN_DIM ||
            region_dy > VIA_REGION_MIN_DIM ) { // avoid regions with 0 dim
            switch(_via_current_shape) {
                case REGION_SHAPE.RECT:
                    var x = Math.round(region_x0 * _via_canvas_scale);
                    var y = Math.round(region_y0 * _via_canvas_scale);
                    var width  = Math.round(region_dx * _via_canvas_scale);
                    var height = Math.round(region_dy * _via_canvas_scale);
                    original_img_region.shape_attributes.set('name', 'rect');
                    original_img_region.shape_attributes.set('x', x);
                    original_img_region.shape_attributes.set('y', y);
                    original_img_region.shape_attributes.set('width', width);
                    original_img_region.shape_attributes.set('height', height);

                    canvas_img_region.shape_attributes.set('name', 'rect');
                    canvas_img_region.shape_attributes.set('x', Math.round( x / _via_canvas_scale ));
                    canvas_img_region.shape_attributes.set('y', Math.round( y / _via_canvas_scale ));
                    canvas_img_region.shape_attributes.set('width', Math.round( width / _via_canvas_scale ));
                    canvas_img_region.shape_attributes.set('height', Math.round( height / _via_canvas_scale ));

                    _via_img_metadata['image'].regions.push(original_img_region);
                    _via_canvas_regions.push(canvas_img_region);

                    break;
                case REGION_SHAPE.POLYGON:
                    // handled by _via_is_user_drawing polygon
                    break;
            }
        } else {
            show_message('Cannot add such a small region');
        }
        update_attributes_panel();
        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
        save_current_data_to_browser_cache();

        return;
    }

});

_via_reg_canvas.addEventListener("mouseover", function(e) {
    // change the mouse cursor icon
    _via_redraw_reg_canvas();
    _via_reg_canvas.focus();
});

_via_reg_canvas.addEventListener('mousemove', function(e) {
    if ( !_via_current_image_loaded ) {
        return;
    }

    _via_current_x = e.offsetX; _via_current_y = e.offsetY;

    if ( _via_is_region_selected ) {
        if ( !_via_is_user_resizing_region ) {
            // check if user moved mouse cursor to region boundary
            // which indicates an intention to resize the reigon

            _via_region_edge = is_on_region_corner(_via_current_x, _via_current_y);

            if ( _via_region_edge[0] === _via_user_sel_region_id ) {
                switch(_via_region_edge[1]) {
                    // rect
                    case 1: // top-left corner of rect
                    case 3: // bottom-right corner of rect
                        _via_reg_canvas.style.cursor = "nwse-resize";
                        break;
                    case 2: // top-right corner of rect
                    case 4: // bottom-left corner of rect
                        _via_reg_canvas.style.cursor = "nesw-resize";
                        break;

                    // circle and ellipse
                    case 5:
                        _via_reg_canvas.style.cursor = "n-resize";
                        break;
                    case 6:
                        _via_reg_canvas.style.cursor = "e-resize";
                        break;

                    default:
                        _via_reg_canvas.style.cursor = "default";
                }

                if (_via_region_edge[1] >= VIA_POLYGON_RESIZE_VERTEX_OFFSET) {
                    // indicates mouse over polygon vertex
                    _via_reg_canvas.style.cursor = "crosshair";
                }
            } else {
                var yes = is_inside_this_region(_via_current_x,
                    _via_current_y,
                    _via_user_sel_region_id);
                if (yes) {
                    _via_reg_canvas.style.cursor = "move";
                } else {
                    _via_reg_canvas.style.cursor = "default";
                }
            }
        }
    }

    if(_via_is_user_drawing_region) {
        // draw region as the user drags the mouse cousor
        if (_via_canvas_regions.length) {
            _via_redraw_reg_canvas(); // clear old intermediate rectangle
        } else {
            // first region being drawn, just clear the full region canvas
            _via_reg_ctx.clearRect(0, 0, _via_reg_canvas.width, _via_reg_canvas.height);
        }

        var region_x0, region_y0;

        if ( _via_click_x0 < _via_current_x ) {
            if ( _via_click_y0 < _via_current_y ) {
                region_x0 = _via_click_x0;
                region_y0 = _via_click_y0;
            } else {
                region_x0 = _via_click_x0;
                region_y0 = _via_current_y;
            }
        } else {
            if ( _via_click_y0 < _via_current_y ) {
                region_x0 = _via_current_x;
                region_y0 = _via_click_y0;
            } else {
                region_x0 = _via_current_x;
                region_y0 = _via_current_y;
            }
        }
        var dx = Math.round(Math.abs(_via_current_x - _via_click_x0));
        var dy = Math.round(Math.abs(_via_current_y - _via_click_y0));

        switch (_via_current_shape ) {
            case REGION_SHAPE.RECT:
                _via_draw_rect_region(region_x0, region_y0, dx, dy, false);
                break;
            case REGION_SHAPE.POLYGON:
                // this is handled by the if ( _via_is_user_drawing_polygon ) { ... }
                // see below
                break;
        }
        _via_reg_canvas.focus();
    }

    if ( _via_is_user_resizing_region ) {
        // user has clicked mouse on bounding box edge and is now moving it
        // draw region as the user drags the mouse cousor
        if (_via_canvas_regions.length) {
            _via_redraw_reg_canvas(); // clear old intermediate rectangle
        } else {
            // first region being drawn, just clear the full region canvas
            _via_reg_ctx.clearRect(0, 0, _via_reg_canvas.width, _via_reg_canvas.height);
        }

        var region_id = _via_region_edge[0];
        var attr = _via_canvas_regions[region_id].shape_attributes;
        switch (attr.get('name')) {
            case REGION_SHAPE.RECT:
                // original rectangle
                var d = [attr.get('x'), attr.get('y'), 0, 0];
                d[2] = d[0] + attr.get('width');
                d[3] = d[1] + attr.get('height');

                var mx = _via_current_x;
                var my = _via_current_y;
                var preserve_aspect_ratio = false;

                // constrain (mx,my) to lie on a line connecting a diagonal of rectangle
                if ( _via_is_ctrl_pressed ) {
                    preserve_aspect_ratio = true;
                }

                rect_update_corner(_via_region_edge[1], d, mx, my, preserve_aspect_ratio);
                rect_standarize_coordinates(d);

                var w = Math.abs(d[2] - d[0]);
                var h = Math.abs(d[3] - d[1]);
                _via_draw_rect_region(d[0], d[1], w, h, true);
                break;

            case REGION_SHAPE.POLYGON:
                var moved_all_points_x = attr.get('all_points_x').slice(0);
                var moved_all_points_y = attr.get('all_points_y').slice(0);
                var moved_vertex_id = _via_region_edge[1] - VIA_POLYGON_RESIZE_VERTEX_OFFSET;

                moved_all_points_x[moved_vertex_id] = _via_current_x;
                moved_all_points_y[moved_vertex_id] = _via_current_y;

                if (moved_vertex_id === 0) {
                    // move both first and last vertex because we
                    // the initial point at the end to close path
                    moved_all_points_x[moved_all_points_x.length-1] = _via_current_x;
                    moved_all_points_y[moved_all_points_y.length-1] = _via_current_y;
                }

                _via_draw_polygon_region(moved_all_points_x,
                    moved_all_points_y,
                    true);
                break;
        }
        _via_reg_canvas.focus();
    }

    if ( _via_is_user_moving_region ) {
        // draw region as the user drags the mouse cousor
        if (_via_canvas_regions.length) {
            _via_redraw_reg_canvas(); // clear old intermediate rectangle
        } else {
            // first region being drawn, just clear the full region canvas
            _via_reg_ctx.clearRect(0, 0, _via_reg_canvas.width, _via_reg_canvas.height);
        }

        var move_x = (_via_current_x - _via_region_click_x);
        var move_y = (_via_current_y - _via_region_click_y);
        var attr = _via_canvas_regions[_via_user_sel_region_id].shape_attributes;

        switch (attr.get('name')) {
            case REGION_SHAPE.RECT:
                _via_draw_rect_region(attr.get('x') + move_x,
                    attr.get('y') + move_y,
                    attr.get('width'),
                    attr.get('height'),
                    true);
                break;

            case REGION_SHAPE.POLYGON:
                var moved_all_points_x = attr.get('all_points_x').slice(0);
                var moved_all_points_y = attr.get('all_points_y').slice(0);
                for (var i=0; i<moved_all_points_x.length; ++i) {
                    moved_all_points_x[i] += move_x;
                    moved_all_points_y[i] += move_y;
                }
                _via_draw_polygon_region(moved_all_points_x,
                    moved_all_points_y,
                    true);
                break;

        }
        _via_reg_canvas.focus();
        return;
    }

    if ( _via_is_user_drawing_polygon ) {
        _via_redraw_reg_canvas();
        var attr = _via_canvas_regions[_via_current_polygon_region_id].shape_attributes;
        var all_points_x = attr.get('all_points_x');
        var all_points_y = attr.get('all_points_y');
        var npts = all_points_x.length;

        var line_x = [all_points_x.slice(npts-1), _via_current_x];
        var line_y = [all_points_y.slice(npts-1), _via_current_y];
        _via_draw_polygon_region(line_x, line_y, false);
    }
});


//
// Canvas update routines
//
function _via_redraw_img_canvas() {
    if (_via_current_image_loaded) {
        _via_img_ctx.clearRect(0, 0, _via_img_canvas.width, _via_img_canvas.height);
        _via_img_ctx.drawImage(_via_current_image, 0, 0,
            _via_img_canvas.width, _via_img_canvas.height);
    }
}

function _via_redraw_reg_canvas() {
    if (_via_current_image_loaded) {
        if ( _via_canvas_regions.length > 0 ) {
            _via_reg_ctx.clearRect(0, 0, _via_reg_canvas.width, _via_reg_canvas.height);
            if (_via_is_region_boundary_visible) {
                draw_all_regions();
            }

            if (_via_is_region_id_visible) {
                draw_all_region_id();
            }
        }
    }
}

function _via_clear_reg_canvas() {
    _via_reg_ctx.clearRect(0, 0, _via_reg_canvas.width, _via_reg_canvas.height);
}

function draw_all_regions() {
    for (var i=0; i < _via_canvas_regions.length; ++i) {
        var attr = _via_canvas_regions[i].shape_attributes;
        var is_selected = _via_canvas_regions[i].is_user_selected;

        switch( attr.get('name') ) {
            case REGION_SHAPE.RECT:
                _via_draw_rect_region(attr.get('x'),
                    attr.get('y'),
                    attr.get('width'),
                    attr.get('height'),
                    is_selected);
                break;
            case REGION_SHAPE.POLYGON:
                _via_draw_polygon_region(attr.get('all_points_x'),
                    attr.get('all_points_y'),
                    is_selected);
                break;
        }
    }
}

// control point for resize of region boundaries
function _via_draw_control_point(cx, cy) {
    _via_reg_ctx.beginPath();
    _via_reg_ctx.arc(cx, cy, VIA_REGION_POINT_RADIUS, 0, 2*Math.PI, false);
    _via_reg_ctx.closePath();

    _via_reg_ctx.fillStyle = VIA_THEME_CONTROL_POINT_COLOR;
    _via_reg_ctx.globalAlpha = 1.0;
    _via_reg_ctx.fill();
}

function _via_draw_rect_region(x, y, w, h, is_selected) {
    if (is_selected) {
        _via_draw_rect(x, y, w, h);

        _via_reg_ctx.strokeStyle = VIA_THEME_SEL_REGION_FILL_BOUNDARY_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_reg_ctx.stroke();

        _via_reg_ctx.fillStyle   = VIA_THEME_SEL_REGION_FILL_COLOR;
        _via_reg_ctx.globalAlpha = VIA_THEME_SEL_REGION_OPACITY;
        _via_reg_ctx.fill();
        _via_reg_ctx.globalAlpha = 1.0;

        _via_draw_control_point(x  ,   y);
        _via_draw_control_point(x+w, y+h);
        _via_draw_control_point(x  , y+h);
        _via_draw_control_point(x+w,   y);
    } else {
        // draw a fill line
        _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_FILL_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_draw_rect(x, y, w, h);
        _via_reg_ctx.stroke();

        if ( w > VIA_THEME_REGION_BOUNDARY_WIDTH &&
            h > VIA_THEME_REGION_BOUNDARY_WIDTH ) {
            // draw a boundary line on both sides of the fill line
            _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_LINE_COLOR;
            _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/4;
            _via_draw_rect(x - VIA_THEME_REGION_BOUNDARY_WIDTH/2,
                y - VIA_THEME_REGION_BOUNDARY_WIDTH/2,
                w + VIA_THEME_REGION_BOUNDARY_WIDTH,
                h + VIA_THEME_REGION_BOUNDARY_WIDTH);
            _via_reg_ctx.stroke();

            _via_draw_rect(x + VIA_THEME_REGION_BOUNDARY_WIDTH/2,
                y + VIA_THEME_REGION_BOUNDARY_WIDTH/2,
                w - VIA_THEME_REGION_BOUNDARY_WIDTH,
                h - VIA_THEME_REGION_BOUNDARY_WIDTH);
            _via_reg_ctx.stroke();
        }
    }
}

function _via_draw_rect(x, y, w, h) {
    _via_reg_ctx.beginPath();
    _via_reg_ctx.moveTo(x  , y);
    _via_reg_ctx.lineTo(x+w, y);
    _via_reg_ctx.lineTo(x+w, y+h);
    _via_reg_ctx.lineTo(x  , y+h);
    _via_reg_ctx.closePath();
}

function _via_draw_circle_region(cx, cy, r, is_selected) {
    if (is_selected) {
        _via_draw_circle(cx, cy, r);

        _via_reg_ctx.strokeStyle = VIA_THEME_SEL_REGION_FILL_BOUNDARY_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_reg_ctx.stroke();

        _via_reg_ctx.fillStyle   = VIA_THEME_SEL_REGION_FILL_COLOR;
        _via_reg_ctx.globalAlpha = VIA_THEME_SEL_REGION_OPACITY;
        _via_reg_ctx.fill();
        _via_reg_ctx.globalAlpha = 1.0;

        _via_draw_control_point(cx + r, cy);
    } else {
        // draw a fill line
        _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_FILL_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_draw_circle(cx, cy, r);
        _via_reg_ctx.stroke();

        if ( r > VIA_THEME_REGION_BOUNDARY_WIDTH ) {
            // draw a boundary line on both sides of the fill line
            _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_LINE_COLOR;
            _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/4;
            _via_draw_circle(cx, cy,
                r - VIA_THEME_REGION_BOUNDARY_WIDTH/2);
            _via_reg_ctx.stroke();
            _via_draw_circle(cx, cy,
                r + VIA_THEME_REGION_BOUNDARY_WIDTH/2);
            _via_reg_ctx.stroke();
        }
    }
}

function _via_draw_circle(cx, cy, r) {
    _via_reg_ctx.beginPath();
    _via_reg_ctx.arc(cx, cy, r, 0, 2*Math.PI, false);
    _via_reg_ctx.closePath();
}

function _via_draw_ellipse_region(cx, cy, rx, ry, is_selected) {
    if (is_selected) {
        _via_draw_ellipse(cx, cy, rx, ry);

        _via_reg_ctx.strokeStyle = VIA_THEME_SEL_REGION_FILL_BOUNDARY_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_reg_ctx.stroke();

        _via_reg_ctx.fillStyle   = VIA_THEME_SEL_REGION_FILL_COLOR;
        _via_reg_ctx.globalAlpha = VIA_THEME_SEL_REGION_OPACITY;
        _via_reg_ctx.fill();
        _via_reg_ctx.globalAlpha = 1.0;

        _via_draw_control_point(cx + rx, cy);
        _via_draw_control_point(cx     , cy - ry);
    } else {
        // draw a fill line
        _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_FILL_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_draw_ellipse(cx, cy, rx, ry);
        _via_reg_ctx.stroke();

        if ( rx > VIA_THEME_REGION_BOUNDARY_WIDTH &&
            ry > VIA_THEME_REGION_BOUNDARY_WIDTH ) {
            // draw a boundary line on both sides of the fill line
            _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_LINE_COLOR;
            _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/4;
            _via_draw_ellipse(cx, cy,
                rx + VIA_THEME_REGION_BOUNDARY_WIDTH/2,
                ry + VIA_THEME_REGION_BOUNDARY_WIDTH/2);
            _via_reg_ctx.stroke();
            _via_draw_ellipse(cx, cy,
                rx - VIA_THEME_REGION_BOUNDARY_WIDTH/2,
                ry - VIA_THEME_REGION_BOUNDARY_WIDTH/2);
            _via_reg_ctx.stroke();
        }
    }
}

function _via_draw_ellipse(cx, cy, rx, ry) {
    _via_reg_ctx.save();

    _via_reg_ctx.beginPath();
    _via_reg_ctx.translate(cx-rx, cy-ry);
    _via_reg_ctx.scale(rx, ry);
    _via_reg_ctx.arc(1, 1, 1, 0, 2 * Math.PI, false);

    _via_reg_ctx.restore(); // restore to original state
    _via_reg_ctx.closePath();

}

function _via_draw_polygon_region(all_points_x, all_points_y, is_selected) {
    if ( is_selected ) {
        _via_reg_ctx.beginPath();
        _via_reg_ctx.moveTo(all_points_x[0], all_points_y[0]);
        for ( var i=1; i < all_points_x.length; ++i ) {
            _via_reg_ctx.lineTo(all_points_x[i], all_points_y[i]);
        }
        _via_reg_ctx.strokeStyle = VIA_THEME_SEL_REGION_FILL_BOUNDARY_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_reg_ctx.stroke();

        _via_reg_ctx.fillStyle   = VIA_THEME_SEL_REGION_FILL_COLOR;
        _via_reg_ctx.globalAlpha = VIA_THEME_SEL_REGION_OPACITY;
        _via_reg_ctx.fill();
        _via_reg_ctx.globalAlpha = 1.0;

        for ( var i=1; i < all_points_x.length; ++i ) {
            _via_draw_control_point(all_points_x[i], all_points_y[i]);
        }
    } else {
        for ( var i=1; i < all_points_x.length; ++i ) {
            // draw a fill line
            _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_FILL_COLOR;
            _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
            _via_reg_ctx.beginPath();
            _via_reg_ctx.moveTo(all_points_x[i-1], all_points_y[i-1]);
            _via_reg_ctx.lineTo(all_points_x[i]  , all_points_y[i]);
            _via_reg_ctx.stroke();

            var slope_i = (all_points_y[i] - all_points_y[i-1]) / (all_points_x[i] - all_points_x[i-1]);
            if ( slope_i > 0 ) {
                // draw a boundary line on both sides
                _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_LINE_COLOR;
                _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/4;
                _via_reg_ctx.beginPath();
                _via_reg_ctx.moveTo(parseInt(all_points_x[i-1]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i-1]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.lineTo(parseInt(all_points_x[i]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.stroke();
                _via_reg_ctx.beginPath();
                _via_reg_ctx.moveTo(parseInt(all_points_x[i-1]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i-1]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.lineTo(parseInt(all_points_x[i]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.stroke();
            } else {
                // draw a boundary line on both sides
                _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_LINE_COLOR;
                _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/4;
                _via_reg_ctx.beginPath();
                _via_reg_ctx.moveTo(parseInt(all_points_x[i-1]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i-1]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.lineTo(parseInt(all_points_x[i]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i]) + parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.stroke();
                _via_reg_ctx.beginPath();
                _via_reg_ctx.moveTo(parseInt(all_points_x[i-1]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i-1]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.lineTo(parseInt(all_points_x[i]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4),
                    parseInt(all_points_y[i]) - parseInt(VIA_THEME_REGION_BOUNDARY_WIDTH/4));
                _via_reg_ctx.stroke();
            }
        }
    }
}

function _via_draw_point_region(cx, cy, is_selected) {
    if (is_selected) {
        _via_draw_point(cx, cy, VIA_REGION_POINT_RADIUS);

        _via_reg_ctx.strokeStyle = VIA_THEME_SEL_REGION_FILL_BOUNDARY_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_reg_ctx.stroke();

        _via_reg_ctx.fillStyle   = VIA_THEME_SEL_REGION_FILL_COLOR;
        _via_reg_ctx.globalAlpha = VIA_THEME_SEL_REGION_OPACITY;
        _via_reg_ctx.fill();
        _via_reg_ctx.globalAlpha = 1.0;
    } else {
        // draw a fill line
        _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_FILL_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/2;
        _via_draw_point(cx, cy, VIA_REGION_POINT_RADIUS);
        _via_reg_ctx.stroke();

        // draw a boundary line on both sides of the fill line
        _via_reg_ctx.strokeStyle = VIA_THEME_BOUNDARY_LINE_COLOR;
        _via_reg_ctx.lineWidth   = VIA_THEME_REGION_BOUNDARY_WIDTH/4;
        _via_draw_point(cx, cy,
            VIA_REGION_POINT_RADIUS - VIA_THEME_REGION_BOUNDARY_WIDTH/2);
        _via_reg_ctx.stroke();
        _via_draw_point(cx, cy,
            VIA_REGION_POINT_RADIUS + VIA_THEME_REGION_BOUNDARY_WIDTH/2);
        _via_reg_ctx.stroke();
    }
}

function _via_draw_point(cx, cy, r) {
    _via_reg_ctx.beginPath();
    _via_reg_ctx.arc(cx, cy, r, 0, 2*Math.PI, false);
    _via_reg_ctx.closePath();
}

function draw_all_region_id() {
    _via_reg_ctx.shadowColor = "transparent";
    for ( var i = 0; i < _via_img_metadata['image'].regions.length; ++i ) {
        var canvas_reg = _via_canvas_regions[i];

        var bbox = get_region_bounding_box(canvas_reg);
        var x = bbox[0];
        var y = bbox[1];
        var w = Math.abs(bbox[2] - bbox[0]);
        var h = Math.abs(bbox[3] - bbox[1]);
        _via_reg_ctx.font = VIA_THEME_ATTRIBUTE_VALUE_FONT;

        var annotation_str  = (i+1);
        var bgnd_rect_width = _via_reg_ctx.measureText(annotation_str).width * 2;

        var char_width  = _via_reg_ctx.measureText('M').width;
        var char_height = 1.8 * char_width;

        var r = _via_img_metadata['image'].regions[i].region_attributes;
        var attribute_value = '';
        if ( r.get("tagging_dic_3_depth_id") ) {
            attribute_value = r.get("tagging_dic_3_depth_nm");
        } else if( r.get("tagging_dic_2_depth_id") ) {
            attribute_value = r.get("tagging_dic_2_depth_nm");
        } else if( r.get("tagging_dic_1_depth_id") ) {
            attribute_value = r.get("tagging_dic_1_depth_nm");
        }
        if( attribute_value ) {
            // show the attribute value
            annotation_str = attribute_value;

            var strw = _via_reg_ctx.measureText(annotation_str).width;

            if ( strw > w ) {
                // if text overflows, crop it
                var str_max     = Math.floor((w * annotation_str.length) / strw);
                annotation_str  = annotation_str.substr(0, str_max-1) + '.';
                bgnd_rect_width = w;
            } else {
                bgnd_rect_width = strw + char_width;
            }
        }

        /*if ( r.size === 1 && w > (2*char_width) ) {
            // show the attribute value
            for (var key of r.keys()) {
                annotation_str = r.get(key);
            }
            var strw = _via_reg_ctx.measureText(annotation_str).width;

            if ( strw > w ) {
                // if text overflows, crop it
                var str_max     = Math.floor((w * annotation_str.length) / strw);
                annotation_str  = annotation_str.substr(0, str_max-1) + '.';
                bgnd_rect_width = w;
            } else {
                bgnd_rect_width = strw + char_width;
            }
        }*/

        if (canvas_reg.shape_attributes.get('name') === REGION_SHAPE.POLYGON) {
            // put label near the first vertex
            x = canvas_reg.shape_attributes.get('all_points_x')[0];
            y = canvas_reg.shape_attributes.get('all_points_y')[0];
        } else {
            // center the label
            x = x - (bgnd_rect_width/2 - w/2);
        }

        // first, draw a background rectangle first
        _via_reg_ctx.fillStyle = 'black';
        _via_reg_ctx.globalAlpha = 0.8;
        _via_reg_ctx.fillRect(Math.floor(x),
            Math.floor(y - 1.1*char_height),
            Math.floor(bgnd_rect_width),
            Math.floor(char_height));

        // then, draw text over this background rectangle
        _via_reg_ctx.globalAlpha = 1.0;
        _via_reg_ctx.fillStyle = 'yellow';
        _via_reg_ctx.fillText(annotation_str,
            Math.floor(x + 0.4*char_width),
            Math.floor(y - 0.35*char_height));

    }
}

function get_region_bounding_box(region) {
    var d = region.shape_attributes;
    var bbox = new Array(4);

    switch( d.get('name') ) {
        case 'rect':
            bbox[0] = d.get('x');
            bbox[1] = d.get('y');
            bbox[2] = d.get('x') + d.get('width');
            bbox[3] = d.get('y') + d.get('height');;
            break;

        case 'polygon':
            var all_points_x = d.get('all_points_x');
            var all_points_y = d.get('all_points_y');

            var minx = Number.MAX_SAFE_INTEGER;
            var miny = Number.MAX_SAFE_INTEGER;
            var maxx = 0;
            var maxy = 0;
            for ( var i=0; i < all_points_x.length; ++i ) {
                if ( all_points_x[i] < minx ) {
                    minx = all_points_x[i];
                }
                if ( all_points_x[i] > maxx ) {
                    maxx = all_points_x[i];
                }
                if ( all_points_y[i] < miny ) {
                    miny = all_points_y[i];
                }
                if ( all_points_y[i] > maxy ) {
                    maxy = all_points_y[i];
                }
            }
            bbox[0] = minx;
            bbox[1] = miny;
            bbox[2] = maxx;
            bbox[3] = maxy;
            break;
    }
    return bbox;
}

//
// Region collision routines
//
function is_inside_region(px, py, descending_order) {
    var N = _via_canvas_regions.length;
    if ( N === 0 ) {
        return -1;
    }
    var start, end, del;
    // traverse the canvas regions in alternating ascending
    // and descending order to solve the issue of nested regions
    if ( descending_order ) {
        start = N - 1;
        end   = -1;
        del   = -1;
    } else {
        start = 0;
        end   = N;
        del   = 1;
    }

    var i = start;
    while ( i !== end ) {
        var yes = is_inside_this_region(px, py, i);
        if (yes) {
            return i;
        }
        i = i + del;
    }
    return -1;
}

function is_inside_this_region(px, py, region_id) {
    var attr   = _via_canvas_regions[region_id].shape_attributes;
    var result = false;
    switch ( attr.get('name') ) {
        case REGION_SHAPE.RECT:
            result = is_inside_rect(attr.get('x'),
                attr.get('y'),
                attr.get('width'),
                attr.get('height'),
                px, py);
            break;

        case REGION_SHAPE.POLYGON:
            result = is_inside_polygon(attr.get('all_points_x'),
                attr.get('all_points_y'),
                px, py);
            break;

    }
    return result;
}

function is_inside_rect(x, y, w, h, px, py) {
    if ( px > x     &&
        px < (x+w) &&
        py > y     &&
        py < (y+h) ) {
        return true;
    } else {
        return false;
    }
}

// returns 0 when (px,py) is outside the polygon
// source: http://geomalgorithms.com/a03-_inclusion.html
function is_inside_polygon(all_points_x, all_points_y, px, py) {
    var wn = 0;    // the  winding number counter

    // loop through all edges of the polygon
    for ( var i = 0; i < all_points_x.length-1; ++i ) {   // edge from V[i] to  V[i+1]
        var is_left_value = is_left( all_points_x[i], all_points_y[i],
            all_points_x[i+1], all_points_y[i+1],
            px, py);

        if (all_points_y[i] <= py) {
            if (all_points_y[i+1]  > py && is_left_value > 0) {
                ++wn;
            }
        }
        else {
            if (all_points_y[i+1]  <= py && is_left_value < 0) {
                --wn;
            }
        }
    }
    if ( wn === 0 ) {
        return 0;
    }
    else {
        return 1;
    }
}

// returns
// >0 if (x2,y2) lies on the left side of line joining (x0,y0) and (x1,y1)
// =0 if (x2,y2) lies on the line joining (x0,y0) and (x1,y1)
// >0 if (x2,y2) lies on the right side of line joining (x0,y0) and (x1,y1)
// source: http://geomalgorithms.com/a03-_inclusion.html
function is_left(x0, y0, x1, y1, x2, y2) {
    return ( ((x1 - x0) * (y2 - y0))  - ((x2 -  x0) * (y1 - y0)) );
}

function is_on_region_corner(px, py) {
    var _via_region_edge = [-1, -1]; // region_id, corner_id [top-left=1,top-right=2,bottom-right=3,bottom-left=4]

    for ( var i = 0; i < _via_canvas_regions.length; ++i ) {
        var attr = _via_canvas_regions[i].shape_attributes;
        var result = false;
        _via_region_edge[0] = i;

        switch ( attr.get('name') ) {
            case REGION_SHAPE.RECT:
                result = is_on_rect_edge(attr.get('x'),
                    attr.get('y'),
                    attr.get('width'),
                    attr.get('height'),
                    px, py);
                break;

            case REGION_SHAPE.POLYGON:
                result = is_on_polygon_vertex(attr.get('all_points_x'),
                    attr.get('all_points_y'),
                    px, py);
                break;
        }

        if (result > 0) {
            _via_region_edge[1] = result;
            return _via_region_edge;
        }
    }
    _via_region_edge[0] = -1;
    return _via_region_edge;
}

function is_on_rect_edge(x, y, w, h, px, py) {
    var dx0 = Math.abs(x - px);
    var dy0 = Math.abs(y - py);
    var dx1 = Math.abs(x + w - px);
    var dy1 = Math.abs(y + h - py);

    //[top-left=1,top-right=2,bottom-right=3,bottom-left=4]
    if ( dx0 < VIA_REGION_EDGE_TOL &&
        dy0 < VIA_REGION_EDGE_TOL ) {
        return 1;
    }
    if ( dx1 < VIA_REGION_EDGE_TOL &&
        dy0 < VIA_REGION_EDGE_TOL ) {
        return 2;
    }
    if ( dx1 < VIA_REGION_EDGE_TOL &&
        dy1 < VIA_REGION_EDGE_TOL ) {
        return 3;
    }

    if ( dx0 < VIA_REGION_EDGE_TOL &&
        dy1 < VIA_REGION_EDGE_TOL ) {
        return 4;
    }
    return 0;
}

function is_on_polygon_vertex(all_points_x, all_points_y, px, py) {
    var n = all_points_x.length;
    for (var i=0; i<n; ++i) {
        if ( Math.abs(all_points_x[i] - px) < VIA_POLYGON_VERTEX_MATCH_TOL &&
            Math.abs(all_points_y[i] - py) < VIA_POLYGON_VERTEX_MATCH_TOL ) {
            return (VIA_POLYGON_RESIZE_VERTEX_OFFSET+i);
        }
    }
    return 0;
}

function rect_standarize_coordinates(d) {
    // d[x0,y0,x1,y1]
    // ensures that (d[0],d[1]) is top-left corner while
    // (d[2],d[3]) is bottom-right corner
    if ( d[0] > d[2] ) {
        // swap
        var t = d[0];
        d[0] = d[2];
        d[2] = t;
    }

    if ( d[1] > d[3] ) {
        // swap
        var t = d[1];
        d[1] = d[3];
        d[3] = t;
    }
}

function rect_update_corner(corner_id, d, x, y, preserve_aspect_ratio) {
    // pre-condition : d[x0,y0,x1,y1] is standarized
    // post-condition : corner is moved ( d may not stay standarized )
    if (preserve_aspect_ratio) {
        switch(corner_id) {
            case 1: // top-left
            case 3: // bottom-right
                var dx = d[2] - d[0];
                var dy = d[3] - d[1];
                var norm = Math.sqrt( dx*dx + dy*dy );
                var nx = dx / norm; // x component of unit vector along the diagonal of rect
                var ny = dy / norm; // y component
                var proj = (x - d[0]) * nx + (y - d[1]) * ny;
                var proj_x = nx * proj;
                var proj_y = ny * proj;
                // constrain (mx,my) to lie on a line connecting (x0,y0) and (x1,y1)
                x = Math.round( d[0] + proj_x );
                y = Math.round( d[1] + proj_y );
                break;
            case 2: // top-right
            case 4: // bottom-left
                var dx = d[2] - d[0];
                var dy = d[1] - d[3];
                var norm = Math.sqrt( dx*dx + dy*dy );
                var nx = dx / norm; // x component of unit vector along the diagonal of rect
                var ny = dy / norm; // y component
                var proj = (x - d[0]) * nx + (y - d[3]) * ny;
                var proj_x = nx * proj;
                var proj_y = ny * proj;
                // constrain (mx,my) to lie on a line connecting (x0,y0) and (x1,y1)
                x = Math.round( d[0] + proj_x );
                y = Math.round( d[3] + proj_y );
                break;
        }
    }

    switch(corner_id) {
        case 1: // top-left
            d[0] = x;
            d[1] = y;
            break;
        case 3: // bottom-right
            d[2] = x;
            d[3] = y;
            break;
        case 2: // top-right
            d[2] = x;
            d[1] = y;
            break;
        case 4: // bottom-left
            d[0] = x;
            d[3] = y;
            break;
    }
}

function _via_update_ui_components() {
    if ( !_via_is_window_resized && _via_current_image_loaded ) {
        show_message('Resizing window ...');
        set_all_text_panel_display('none');
        show_all_canvas();

        _via_is_window_resized = true;
        show_image(_via_image_index);

        if (_via_is_canvas_zoomed) {
            reset_zoom_level();
        }
    }
}

//
// Shortcut key handlers
//

window.addEventListener('keyup', function(e) {
    if (_via_is_user_updating_attribute_value ||
        _via_is_user_updating_attribute_name  ||
        _via_is_user_adding_attribute_name) {

        return;
    }

    if ( e.which === 17 ) { // Ctrl key
        _via_is_ctrl_pressed = false;
    }
});

window.addEventListener('keydown', function(e) {
    if (_via_is_user_updating_attribute_value ||
        _via_is_user_updating_attribute_name  ||
        _via_is_user_adding_attribute_name) {

        return;
    }

    // user commands
    if ( e.ctrlKey ) {
        _via_is_ctrl_pressed = true;
        /*if ( e.which === 83 ) { // Ctrl + s
          download_all_region_data('json');
          e.preventDefault();
          return;
        }*/

        if ( e.which === 65 ) { // Ctrl + a
            sel_all_regions();
            update_attributes_panel();
            e.preventDefault();
            return;
        }

        /*if ( e.which === 67 ) { // Ctrl + c
            if (_via_is_region_selected ||
                _via_is_all_region_selected) {
                copy_sel_regions();
                e.preventDefault();
            }
            return;
        }*/

        /*if ( e.which === 86 ) { // Ctrl + v
            paste_sel_regions();
            e.preventDefault();
            return;
        }*/
    }

    if( e.which === 46 /*|| e.which === 8*/) { // Del or Backspace
        del_sel_regions();
        e.preventDefault();
    }

    // zoom
    if (_via_current_image_loaded) {
        // see: http://www.javascripter.net/faq/keycodes.htm
        if (e.which === 61 || e.which === 187) { // + for zoom in
            if (e.shiftKey) {
                zoom_in();
            } else {  // = for zoom reset
                reset_zoom_level();
            }
            return;
        }

        if (e.which === 173 || e.which === 189) { // - for zoom out
            zoom_out();
            return;
        }
    }

    if ( e.which === 27 ) { // Esc
        if (_via_is_user_updating_attribute_value ||
            _via_is_user_updating_attribute_name ||
            _via_is_user_adding_attribute_name) {

            _via_is_user_updating_attribute_value = false;
            _via_is_user_updating_attribute_name = false;
            _via_is_user_adding_attribute_name = false;
            update_attributes_panel();
        }

        if ( _via_is_user_resizing_region ) {
            // cancel region resizing action
            _via_is_user_resizing_region = false;
        }

        if ( _via_is_region_selected ) {
            // clear all region selections
            _via_is_region_selected = false;
            _via_user_sel_region_id = -1;
            toggle_all_regions_selection(false);
            update_attributes_panel();
        }

        if ( _via_is_user_drawing_polygon ) {
            _via_is_user_drawing_polygon = false;
            _via_canvas_regions.splice(_via_current_polygon_region_id, 1);
        }

        if ( _via_is_user_drawing_region ) {
            _via_is_user_drawing_region = false;
        }

        if ( _via_is_user_resizing_region ) {
            _via_is_user_resizing_region = false
        }

        if ( _via_is_user_updating_attribute_name ||
            _via_is_user_updating_attribute_value) {
            _via_is_user_updating_attribute_name = false;
            _via_is_user_updating_attribute_value = false;
        }

        if ( _via_is_user_moving_region ) {
            _via_is_user_moving_region = false
        }

        e.preventDefault();
        _via_redraw_reg_canvas();
        return;
    }
});

//
// Mouse wheel event listener
//
window.addEventListener('wheel', function(e) {
    if (!_via_current_image_loaded) {
        return;
    }

    if (e.ctrlKey) {
        if (e.deltaY < 0) {
            zoom_in();
        } else {
            zoom_out();
        }
        e.preventDefault();
    }
});

function del_sel_regions() {
    if ( !_via_current_image_loaded ) {
        show_message('Select image!');
        return;
    }

    var del_region_count = 0;
    if ( _via_is_all_region_selected ) {
        del_region_count = _via_canvas_regions.length;
        _via_canvas_regions.splice(0);
        _via_img_metadata['image'].regions.splice(0);
    } else {
        var sorted_sel_reg_id = [];
        for ( var i = 0; i < _via_canvas_regions.length; ++i ) {
            if ( _via_canvas_regions[i].is_user_selected ) {
                sorted_sel_reg_id.push(i);
            }
        }
        sorted_sel_reg_id.sort( function(a,b) {
            return (b-a);
        });
        for ( var i = 0; i < sorted_sel_reg_id.length; ++i ) {
            _via_canvas_regions.splice( sorted_sel_reg_id[i], 1);
            _via_img_metadata['image'].regions.splice( sorted_sel_reg_id[i], 1);
            del_region_count += 1;
        }
    }

    _via_is_all_region_selected = false;
    _via_is_region_selected     = false;
    _via_user_sel_region_id     = -1;

    if ( _via_canvas_regions.length === 0 ) {
        // all regions were deleted, hence clear region canvas
        _via_clear_reg_canvas();
        $('#attributes_panel_table').hide();
        _via_reg_canvas.focus();
    } else {
        _via_redraw_reg_canvas();
    }
    _via_reg_canvas.focus();
    update_attributes_panel();
    save_current_data_to_browser_cache();

    show_message('Deleted ' + del_region_count + ' selected regions');
}

function sel_all_regions() {
    if (!_via_current_image_loaded) {
        show_message('Select image!');
        return;
    }

    toggle_all_regions_selection(true);
    _via_is_all_region_selected = true;
    _via_redraw_reg_canvas();
}

function copy_sel_regions() {
    if (!_via_current_image_loaded) {
        show_message('Select image!');
        return;
    }

    if (_via_is_region_selected ||
        _via_is_all_region_selected) {
        _via_copied_image_regions.splice(0);
        for ( var i = 0; i < _via_img_metadata['image'].regions.length; ++i ) {
            var img_region = _via_img_metadata['image'].regions[i];
            var canvas_region = _via_canvas_regions[i];
            if ( canvas_region.is_user_selected ) {
                _via_copied_image_regions.push( clone_image_region(img_region) );
            }
        }
        show_message('Copied ' + _via_copied_image_regions.length +
            ' selected regions. Press Ctrl + v to paste');
    } else {
        show_message('Select a region first!');
    }
}

function paste_sel_regions() {
    if ( !_via_current_image_loaded ) {
        show_message('Select image!');
        return;
    }

    if ( _via_copied_image_regions.length ) {
        var pasted_reg_count = 0;
        for ( var i = 0; i < _via_copied_image_regions.length; ++i ) {
            // ensure copied the regions are within this image's boundaries
            var bbox = get_region_bounding_box( _via_copied_image_regions[i] );
            if (bbox[2] < _via_current_image_width &&
                bbox[3] < _via_current_image_height) {
                _via_img_metadata['image'].regions.push( _via_copied_image_regions[i] );

                pasted_reg_count += 1;
            }
        }
        _via_load_canvas_regions();
        var discarded_reg_count = _via_copied_image_regions.length - pasted_reg_count;
        show_message('Pasted ' + pasted_reg_count + ' regions. ' +
            'Discarded ' + discarded_reg_count + ' regions exceeding image boundary.');
        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
    } else {
        show_message('To paste a region, you first need to select a region and copy it!');
    }
}

function set_zoom() {
    _via_is_canvas_zoomed = true;
    var zoom_scale = VIA_CANVAS_ZOOM_LEVELS[_via_canvas_zoom_level_index];
    set_all_canvas_scale(zoom_scale);
    set_all_canvas_size(_via_canvas_width  * zoom_scale,
        _via_canvas_height * zoom_scale);
    _via_canvas_scale = _via_canvas_scale_without_zoom / zoom_scale;

    _via_load_canvas_regions(); // image to canvas space transform
    _via_redraw_img_canvas();
    _via_redraw_reg_canvas();
    _via_reg_canvas.focus();

    show_message('Loaded image. Zoomed in to level ' + zoom_scale + 'X');
}

function reset_zoom_level() {
    if (!_via_current_image_loaded) {
        show_message('Select image!');
        return;
    }
    if (_via_is_canvas_zoomed) {
        _via_is_canvas_zoomed = false;
        _via_canvas_zoom_level_index = VIA_CANVAS_DEFAULT_ZOOM_LEVEL_INDEX

        var zoom_scale = VIA_CANVAS_ZOOM_LEVELS[_via_canvas_zoom_level_index];
        set_all_canvas_scale(zoom_scale);
        set_all_canvas_size(_via_canvas_width, _via_canvas_height);
        _via_canvas_scale = _via_canvas_scale_without_zoom;

        _via_load_canvas_regions(); // image to canvas space transform
        _via_redraw_img_canvas();
        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
        show_message('Zoom reset');
    } else {
        show_message('Cannot reset zoom because image zoom has not been applied!');
    }
}

function zoom_in() {
    if (!_via_current_image_loaded) {
        show_message('Select image!');
        return;
    }

    if (_via_canvas_zoom_level_index === (VIA_CANVAS_ZOOM_LEVELS.length-1)) {
        show_message('Further zoom-in not possible');
    } else {
        _via_canvas_zoom_level_index += 1;

        _via_is_canvas_zoomed = true;
        var zoom_scale = VIA_CANVAS_ZOOM_LEVELS[_via_canvas_zoom_level_index];
        set_all_canvas_scale(zoom_scale);
        set_all_canvas_size(_via_canvas_width  * zoom_scale,
            _via_canvas_height * zoom_scale);
        _via_canvas_scale = _via_canvas_scale_without_zoom / zoom_scale;

        _via_load_canvas_regions(); // image to canvas space transform
        _via_redraw_img_canvas();
        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
        show_message('Zoomed in to level ' + zoom_scale + 'X');
    }
}

function zoom_out() {
    if (!_via_current_image_loaded) {
        show_message('Select image!');
        return;
    }

    if (_via_canvas_zoom_level_index === 0) {
        show_message('Further zoom-out not possible');
    } else {
        _via_canvas_zoom_level_index -= 1;

        _via_is_canvas_zoomed = true;
        var zoom_scale = VIA_CANVAS_ZOOM_LEVELS[_via_canvas_zoom_level_index];
        set_all_canvas_scale(zoom_scale);
        set_all_canvas_size(_via_canvas_width  * zoom_scale,
            _via_canvas_height * zoom_scale);
        _via_canvas_scale = _via_canvas_scale_without_zoom / zoom_scale;

        _via_load_canvas_regions(); // image to canvas space transform
        _via_redraw_img_canvas();
        _via_redraw_reg_canvas();
        _via_reg_canvas.focus();
        show_message('Zoomed out to level ' + zoom_scale + 'X');
    }
}

function toggle_region_boundary_visibility() {
    _via_is_region_boundary_visible = !_via_is_region_boundary_visible;
    _via_redraw_reg_canvas();
    _via_reg_canvas.focus();
}

function toggle_region_id_visibility() {
    _via_is_region_id_visible = !_via_is_region_id_visible;
    _via_redraw_reg_canvas();
    _via_reg_canvas.focus();
}

//
// Persistence of annotation data in browser cache (i.e. localStorage)
//

function check_local_storage() {
    // https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API/Using_the_Web_Storage_API
    try {
        var x = '__storage_test__';
        localStorage.setItem(x, x);
        localStorage.removeItem(x);
        return true;
    }
    catch(e) {
        return false;
    }
}

function save_current_data_to_browser_cache() {
    setTimeout(function() {
        if ( _via_is_local_storage_available &&
            ! _via_is_save_ongoing) {
            try {
                _via_is_save_ongoing = true;
                var img_metadata = pack_via_metadata();
                var timenow = new Date().toUTCString();
                localStorage.setItem('_via_timestamp', timenow);
                localStorage.setItem('_via_img_metadata', img_metadata[0]);
                // save attributes
                var attr = [];
                for (var attribute of _via_region_attributes) {
                    attr.push(attribute);
                }
                localStorage.setItem('_via_region_attributes', JSON.stringify(attr));
                _via_is_save_ongoing = false;
                localStorage.setItem('_is_save', 'false');
            } catch(err) {
                _via_is_save_ongoing = false;
                _via_is_local_storage_available = false;
                show_message('Failed to save annotation data to browser cache.');
                alert('Failed to save annotation data to browser cache.');
                console.log('Failed to save annotation data to browser cache');
                console.log(err.message);
            }
        }
    }, 200);
}

function is_via_data_in_localStorage() {
    if ( localStorage.getItem('_via_timestamp') &&
        localStorage.getItem('_via_img_metadata') ) {
        return true;
    } else {
        return false;
    }
}

function clear_localStorage() {
    localStorage.clear();
}

function download_localStorage_data(type) {
    var saved_date = new Date( localStorage.getItem('_via_timestamp') );

    var localStorage_data_blob = new Blob( [localStorage.getItem('_via_img_metadata')],
        {type: 'text/json;charset=utf-8'});

    save_data_to_local_file(localStorage_data_blob, 'VIA_browser_cache_' + saved_date + '.json');
}

//
// Handlers for attributes input panel (spreadsheet like user input panel)
//
function attr_input_focus(i) {
    if ( _via_is_reg_attr_panel_visible ) {
        select_only_region(i);
        _via_redraw_reg_canvas();
    }
    _via_is_user_updating_attribute_value=true;
}

function attr_input_blur(i) {
    if ( _via_is_reg_attr_panel_visible ) {
        set_region_select_state(i, false);
        _via_redraw_reg_canvas();
    }
    _via_is_user_updating_attribute_value=false;
}
function get_attribute_list(depth, upperImageTaggingDataDicId) {
    var data = attribute_map[upperImageTaggingDataDicId];
    var selectBox = $('#attributes_panel_table #tagging_dic_'+depth+'_depth');
    selectBox.empty();
    selectBox.append( $('<option>', {'value': '', 'text': '선택'}) );
    if(data) {
        if(upperImageTaggingDataDicId) {
            $.each(data, function(i, v) {
                selectBox.append( $('<option>', {'value': v.imageTaggingDataDicIdSq, 'text': v.imageTaggingDataDicNm}) );
            });
        }
    } else {

    }
    /*$.ajax({
        async: false,
        url: 'getTaggingDicList',
        method: 'post',
        data: {upperImageTaggingDataDicId: upperImageTaggingDataDicId},
        dataType: 'json',
        success: function(data) {
            var selectBox = $('#attributes_panel_table #tagging_dic_'+depth+'_depth');
            selectBox.empty();
            selectBox.append( $('<option>', {'value': '', 'text': '선택'}) );
            $.each(data, function(i, v) {
                selectBox.append( $('<option>', {'value': v.imageTaggingDataDicIdSq, 'text': v.imageTaggingDataDicNm}) );
            });
        }
    });*/
}
function init_spreadsheet_input(type, col_headers, data, row_names) {
    var user_selected_shape_count = 0;
    for ( var i=0; i < data.length; ++i ) {
        if ( data[i].is_user_selected ) {
            user_selected_shape_count++;

            var region = data[i].region_attributes;

            // tagging_1_depth value
            $('#attributes_panel_table #tagging_dic_1_depth').attr('onchange', "update_attribute_value('" + i + "', 1)");
            get_attribute_list(2, region.get('tagging_dic_1_depth_id'));
            $('#attributes_panel_table #tagging_dic_1_depth').val(region.get('tagging_dic_1_depth_id'));
            // tagging_2_depth value
            $('#attributes_panel_table #tagging_dic_2_depth').attr('onchange', "update_attribute_value('" + i + "', 2)");
            get_attribute_list(3, region.get('tagging_dic_2_depth_id'));
            $('#attributes_panel_table #tagging_dic_2_depth').val(region.get('tagging_dic_2_depth_id'));
            // tagging_3_depth value
            $('#attributes_panel_table #tagging_dic_3_depth').attr('onchange', "update_attribute_value('" + i + "', 3)");
            $('#attributes_panel_table #tagging_dic_3_depth').val(region.get('tagging_dic_3_depth_id'));

            var shape = data[i].shape_attributes;
            $('#attributes_panel_table #shape').text(shape.get('name'));

            var shape_info = '';
            if( REGION_SHAPE.RECT ==  shape.get('name') ) {
                // x, y, width, height
                shape_info += 'x: ' + shape.get('x') + '<br />';
                shape_info += 'y: ' + shape.get('y') + '<br />';
                shape_info += 'width: ' + shape.get('width') + '<br />';
                shape_info += 'height: ' + shape.get('height') + '<br />';
            } else if( REGION_SHAPE.POLYGON ==  shape.get('name') ) {
                // x points, y points (all_points_x, all_points_y)
                var all_points_x = shape.get('all_points_x');
                var all_points_y = shape.get('all_points_y');
                for(var pCnt = 0; pCnt < all_points_x.length; pCnt++) {
                    shape_info += pCnt + ': ' + all_points_x[pCnt] +
                        ', ' + all_points_y[pCnt] + '<br />';
                }
            }
            $('#attributes_panel_table #shape_info').html(shape_info);
            $('#attributes_panel_toolbar').show();
            $('#attributes_panel_table').show();
        }
    }
    if( user_selected_shape_count != 1 ) {
        $('#attributes_panel_toolbar').hide();
        $('#attributes_panel_table').hide();
        $('#attributes_panel_table #label').val('');
        $('#attributes_panel_table #shape').val('');
        $('#attributes_panel_table #shape_info').html('');
    }
}
function update_attributes_panel() {
    // if (_via_current_image_loaded && _via_is_attributes_panel_visible) {
    //     if (_via_is_reg_attr_panel_visible) {
            update_region_attributes_input_panel();
        // }
    // }
}

function update_region_attributes_input_panel() {
    init_spreadsheet_input('region_attributes',
        _via_region_attributes,
        _via_img_metadata['image'].regions);

}

function toggle_attributes_input_panel() {
    if( _via_is_reg_attr_panel_visible ) {
        toggle_reg_attr_panel();
    }
}

function toggle_reg_attr_panel() {
    if ( _via_current_image_loaded ) {
        if ( _via_is_attributes_panel_visible ) {
            if( _via_is_reg_attr_panel_visible ) {
                // attributes_panel.style.display   = 'none';
                _via_is_attributes_panel_visible = false;
                _via_is_reg_attr_panel_visible   = false;
                _via_reg_canvas.focus();
            } else {
                update_region_attributes_input_panel();
                _via_is_reg_attr_panel_visible  = true;
                attributes_panel.focus();
            }
        } else {
            _via_is_attributes_panel_visible = true;
            update_region_attributes_input_panel();
            _via_is_reg_attr_panel_visible = true;
            attributes_panel.style.display = 'block';
            attributes_panel.focus();
        }
    } else {
        show_message('Please load some images first');
    }
}
function update_attribute_value(region_id, depth) {
    var region = _via_img_metadata['image'].regions[region_id];
    var selectBox = $('#attributes_panel_table #tagging_dic_' + depth + '_depth');
    var attribute_nm = selectBox.attr('id');
    var dic_id = selectBox.val();
    var dic_nm = selectBox.val() == '' ? '' : selectBox.find(':selected').text();
    region.region_attributes.set(attribute_nm + '_id', dic_id);
    region.region_attributes.set(attribute_nm + '_nm', dic_nm);

    if( depth == 1 ) {
        // get_attribute_list(2, dic_id);
        region.region_attributes.set('tagging_dic_2_depth_id', '');
        region.region_attributes.set('tagging_dic_2_depth_nm', '');

    } else if( depth == 2 ) {
        // get_attribute_list(3, dic_id);
        region.region_attributes.set('tagging_dic_3_depth_id', '');
        region.region_attributes.set('tagging_dic_3_depth_nm', '');
    }

    if( depth != 3 && dic_id == '' ) {
        region.region_attributes.set('tagging_dic_3_depth_id', '');
        region.region_attributes.set('tagging_dic_3_depth_nm', '');
    }

    update_region_attributes_input_panel();
    if (_via_is_reg_attr_panel_visible) {
        set_region_select_state(region_id, false);
    }
    _via_redraw_reg_canvas();
    _via_is_user_updating_attribute_value = false;
    save_current_data_to_browser_cache();
}
/*function update_attribute_value(attr_id, value) {
    var attr_id_split = attr_id.split('#');
    var type = attr_id_split[0];
    var attribute_name = attr_id_split[1];
    var region_id = attr_id_split[2];

    switch(type) {
        case 'r': // region attribute
            var region = _via_img_metadata['image'].regions[region_id];
            region.region_attributes.set(attribute_name, value);
            update_region_attributes_input_panel();
            break;
    }
    if (_via_is_reg_attr_panel_visible) {
        set_region_select_state(region_id, false);
    }
    _via_redraw_reg_canvas();
    _via_is_user_updating_attribute_value = false;
    save_current_data_to_browser_cache();
}*/

function add_new_attribute(type, attribute_name) {
    switch(type) {
        case 'r': // region attribute
            if ( !_via_region_attributes.has(attribute_name) ) {
                _via_region_attributes.add(attribute_name);
            }
            update_region_attributes_input_panel();
            break;
    }
    _via_is_user_adding_attribute_name = false;
}

//
// hooks for sub-modules
// implemented by sub-modules
//
//function _via_hook_next_image() {}
//function _via_hook_prev_image() {}
