(function($){
	$.fn.extend({
		wizard: function(o) {
			var o = $.extend({
				validate: function(i, elem) { return true },
				render: function(i, elem) { }
			}, o);
			return this.each(function() {
				var obj = $(this);
				var stepAnchors = obj.find('> ul:first > li > a');
				applyCSS(stepAnchors, "wiz-anc-default");
				var stepContent = stepAnchors.map(function() {
					return obj.find($(this).attr('href')).get();
				});
				var curStepIdx = -1;
				
				stepContent.hide();
				selectStep(0);
				
				stepAnchors.bind('click', function(e) {
                    e.preventDefault();
                    var isDone = $(this).attr("isDone");
                    if (isDone == true) {
                        var idx = stepAnchors.index(this);  
                        selectStep(idx);
                    }
                });
				
				obj.find('.next').bind('click', function(e) {
					e.preventDefault();
					selectStep(curStepIdx+1);
				});
				
				obj.find('.back').bind('click', function(e) {
					e.preventDefault();
					selectStep(curStepIdx-1);
				});
				
				function selectStep(idx) {
					if (idx < 0 || idx >= stepAnchors.size() || idx == curStepIdx)
						return;
					if (curStepIdx < 0 || o.validate(curStepIdx, stepContent.eq(curStepIdx))) {
						stepContent.hide();
						if (curStepIdx >= 0)
							applyCSS(stepAnchors.eq(curStepIdx), "wiz-anc-done"); 
						applyCSS(stepAnchors.eq(idx).attr('isDone', '1'), "wiz-anc-current");
						o.render(idx, stepContent.eq(idx));
						stepContent.eq(idx).fadeIn();
						curStepIdx = idx;
					}
				}
				
                function applyCSS(elem, css){
                    elem.removeClass("wiz-anc-default");
                    elem.removeClass("wiz-anc-current");
                    elem.removeClass("wiz-anc-done");
                    elem.addClass(css); 
                }
			});
		}
	});
})(jQuery);