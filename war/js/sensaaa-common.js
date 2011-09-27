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

  $.fn.loginLink = function(settings) {    
    var defaults = {
        baseUrl: '.',
        loggedInMsg: 'Welcome, <span class="loginName"/>&nbsp;<a class="logoutLink">Logout</a>',
        loggedOutMsg: 'Welcome, Guest.&nbsp;<a class="loginLink">Login</a>',
        currentUserUrl: '/rest/user/current',
        loginUrl: '/rest/user/login',
        logoutUrl: '/rest/user/logout',
        complete: function(user) {}
    };
    var option = $.extend(defaults, settings);   // Merge the passed parameters with the defaults
    
    return this.each(function(index, domEle) {
      jQuery.ajax({
        url: option.baseUrl + option.currentUserUrl,
        type: 'GET',
        success: function(data) {
          var me = $(domEle);
          me.children().remove();
          me.append(option.loggedInMsg);
          me.find('.loginName').text(data.email);
          var url = option.baseUrl + option.logoutUrl;
          if (option.returnTo) {
            url += '?redirectTo=' + option.returnTo;
          }
          me.find('a.logoutLink').attr('href', url);
          option.complete(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
          var me = $(domEle);
          me.children().remove();
          me.append(option.loggedOutMsg);
          var url = option.baseUrl + option.loginUrl;
          if (option.returnTo) {
            url += '?redirectTo=' + option.returnTo;
          }
          me.find('a.loginLink').attr('href', url);
          option.complete();
        }
      });    
    });
  };

})(jQuery);