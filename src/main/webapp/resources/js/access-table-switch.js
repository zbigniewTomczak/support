$(".toogle-switch").bootstrapSwitch({onColor: 'success', offColor: 'danger'});
    $('.toogle-switch.write').on('switchChange.bootstrapSwitch', function(event, state) {
        if (state) {
           var key = $(this).prop("class")
          .replace('toogle-switch','')
          .replace('write','')
          .replace(/(\s)/g,'');
          $('div.'+key).find('.read').bootstrapSwitch('state', true);
        }
    });
    $('.toogle-switch.read').on('switchChange.bootstrapSwitch', function(event, state) {
        if (!state) {
           var key = $(this).prop("class")
          .replace('toogle-switch','')
          .replace('read','')
          .replace(/(\s)/g,'');
          $('div.'+key).find('.write').bootstrapSwitch('state', false);
        }
    });