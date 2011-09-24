jQuery.extend({
    put: function(url, data, callback, type, error) {
        jQuery.ajax({type: 'PUT', url: url, data: data, success: callback, dataType: type, error: error});
    },
    delete: function(url, data, callback, type, error) {
      jQuery.ajax({type: 'DELETE', url: url, data: data, success: callback, dataType: type, error: error});
  }
});


(function($){  
  $.fn.addFloatReset = function() {
    return this.each(function(index, domEle) {
      jQuery(domEle).append('<br style="clear:both;width:1px; height:1px"/>');
    });
  };
})(jQuery);