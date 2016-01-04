
window.twist = {};

function ScenarioProcessor(theParent) {
  this.parent = theParent;
}

ScenarioProcessor.prototype.clear = function() {
  this.parent.innerHTML = '';
}

ScenarioProcessor.prototype.displayNoFailures = function() {
  var scenarioContainer = $('#scenarioContainer');
  var congratulations = $('#congratulationsHidden').clone();
  congratulations.attr("id", "congratulations");
  scenarioContainer.prepend(congratulations);
  congratulations.css("display", "block");
}

ScenarioProcessor.prototype.process = function(scenario) {

  var thisScenario = scenario;
  var scenarioContainer = this.parent;

  function beginProcessing() {
    clearScenarioContainer();
    renderName();

    if (scenario.type == 'table_driven_scenario') {
      renderDataTable();
    }
    else {
      renderScenario();
    }
  }

  function clearScenarioContainer() {
    scenarioContainer.innerHTML = '';
  }

  function renderName() {
    renderNextPreviousButtons(scenarioContainer);
    var h2 = createElement('h2', {}, thisScenario.name);
    h2.appendChild(createElement('span', {}, formatTime(thisScenario.executionTime)));
    scenarioContainer.appendChild(h2);
  }

  function renderNextPreviousButtons(parent) {
    var div = createElement('span', {class: 'next_prev_actions'});
    var previous = createElement('span', {id: 'previous', 'data-action': 'previous'}, createElement('img', {src: 'images/previous.png'}));
    var next = createElement('span', {id: 'next', 'data-action': 'next'}, createElement('img', {src: 'images/next.png'}));
    div.appendChild(previous);
    div.appendChild(next);
    parent.appendChild(div);
  }

  function renderDataTable() {
    var table = createElement('table', {border: 1, class: 'align-center'});
    createTableHeader(table, thisScenario.table.columns);

    var tbody = document.createElement('tbody');
    table.appendChild(tbody);

    for (var i = 0; i < thisScenario.runs.length; i++){
      var cssClass = thisScenario.runs[i].executionStatus;
      addTableRow(tbody, thisScenario.table.rows[i], cssClass, {'data-run-index': i});
    }

    $(table).on('click', 'tr', function() {
      var runIndex = $(this).data('run-index');
      thisScenario = scenario.runs[runIndex];
      renderScenario();
    });

    var div = document.createElement('div');
    div.setAttribute('id', 'datatable_container');
    div.appendChild(table);

    scenarioContainer.appendChild(div);
    $($(table).find('tr').get(1)).trigger('click');
  }
  

  function renderScenario() {
    var scenarioDetailsContainer = document.getElementById('scenario_details_container');
    if (scenarioDetailsContainer) {
      scenarioDetailsContainer.innerHTML = '';
    }
    else {
      scenarioDetailsContainer = document.createElement('div');
      scenarioDetailsContainer.setAttribute('id', 'scenario_details_container');
      scenarioContainer.appendChild(scenarioDetailsContainer);
    }

    renderTagsAndContexts(scenarioDetailsContainer);
    renderElements(scenarioDetailsContainer);
    renderErrors(scenarioDetailsContainer);
    if(thisScenario.dataStore != null){
      if(thisScenario.dataStore.keyValues.length > 0){
        renderDataStore(scenarioDetailsContainer);
      }
    }
    if (window.twist.onScenarioDisplayed) {
      window.twist.onScenarioDisplayed(thisScenario);
    }
  }

  function renderTagsAndContexts(scenarioDetailsContainer) {
    var div = createElement('div', {class: 'contentSection doubleBorderTopBottom'})
    renderTags(div);
    renderContexts(div);
    scenarioDetailsContainer.appendChild(div);
  }

  function renderTags(parent) {
    var tags = createElement('div', {id: 'tagsHolder', class: 'scenario_tags'});
    tags.appendChild(createElement('span', {}, 'tags:'));

    var ul = createElement('ul');
    for (var i = 0; i < thisScenario.tags.length; i++) {
      ul.appendChild(createElement('li', {}, thisScenario.tags[i].name));
    } 

    tags.appendChild(ul);
    parent.appendChild(tags);
  }

  function renderContexts(parent) {
    var contextsContainer = createElement('div', {id: 'contextHolder', class: 'scenario_contexts'});
    contextsContainer.appendChild(createElement('span', {}, 'context:'));
    var contextsUL = createElement('ul');

    for (var i = 0; i < thisScenario.contexts.length; i++) {
      var thisContext = thisScenario.contexts[i];
      var li = createElement('li');
      renderSentence(li, thisContext.sentence);
      contextsUL.appendChild(li);
    }

    contextsContainer.appendChild(contextsUL);
    parent.appendChild(contextsContainer);
  }

  function renderElements(parent){
    var elementsContainer = createElement('div', {class: 'elements_container contentSection'});
    var renderers = {workflow: renderWorkflow, comment: renderComment, table: renderBRT};
    for (var i = 0; i < thisScenario.elements.length; i++) {
      thisElement = thisScenario.elements[i];
      var renderer = renderers[thisElement.type];
      if (renderer) {
        renderer(elementsContainer, thisElement);
      }
    }
    parent.appendChild(elementsContainer);
  }

  function renderDataStore(parent){
    var dataStore = thisScenario.dataStore;
    var div = createElement('div', {class: 'data_store_container'});

    div.appendChild(createElement('h4', {}, 'Scenario Store:'));
    var dataStoreTable = createElement('table', {class: 'data_store'});
    dataStoreTable.setAttribute('border', 1);
    var header = ["Key" ,"Value"];
    createTableHeader(dataStoreTable, header );
    var tbody = document.createElement('tbody');
    for(var i = 0; i<dataStore.keyValues.length; i++){
      addTableRow(tbody, dataStore.keyValues[i], 'data_store_row' );
    }

    dataStoreTable.appendChild(tbody);
    div.appendChild(dataStoreTable);
    parent.appendChild(div);
    

  }

  function renderErrors(scenarioDetailsContainer) {
    var errors = thisScenario.errorDetails;
    if (!errors) {
      return;
    }

    var table = createElement('table', {id: 'exceptionHolder'});
    var tbody = createElement('tbody');

    for (var i = 0; i < errors.stackTraces.length; i++) {
      var tr = createElement('tr');

      var td1 = createElement('td');
      var div1 = createElement('div', {class: 'image'});

      if(errors.screenshots[i] != null) { 
           var image = createElement('a', {href: errors.screenshots[i].imagePath, rel: 'lightbox'}, 
           createElement('img', {src: errors.screenshots[i].thumbnailPath}));
           div1.appendChild(image);
       }

      td1.appendChild(div1);

      var td2 = createElement('td', {class: 'code'}, createElement('pre', {}, errors.stackTraces[i]));

      tr.appendChild(td1);
      tr.appendChild(td2);

      tbody.appendChild(tr);
      
    }

    table.appendChild(tbody);
    scenarioDetailsContainer.appendChild(table);

  }

  function renderWorkflow(parent, element){
    parent.appendChild(createElement('h4', {class: 'workflow_title'}, element.title)); 
    var ul = createElement('ul', {class: 'scenarioStatus'});
    renderSteps(ul, element.steps);
    parent.appendChild(ul);
  }
  
  function renderComment(parent, comment){
    var commentContainer = createElement('div', {class: 'comment_container'});
    if (comment.text.length == 0) {
      commentContainer.appendChild(createElement('br'));
    }
    else {
      renderSentence(commentContainer, comment.sentence);
    }

    parent.appendChild(commentContainer);

  } 

  function renderBRT(parent, element) {
    var brtContainer = createElement('div', {class: 'brt_container'});
    var table = createElement('table', {class: 'align-center brt'});
    table.setAttribute('border', 1);
    createTableHeader(table, element.columns);
    for (var i = 0; i < element.rows.length; i++) {
      var thisRow = element.rows[i];
      var tr = createElement('tr');
      for (var j = 0; j < thisRow.cells.length; j++) {
        var thisCell = thisRow.cells[j];
        var cellText = thisCell.isPassed ? thisCell.text : thisCell.errorMessage;
        tr.appendChild(createElement('td', {class: thisCell.isPassed ? 'passed' : 'failed'}, cellText));
      }
      table.appendChild(tr);
    }
    brtContainer.appendChild(table);
    parent.appendChild(brtContainer);
  }

  function renderSteps(parent, steps) {
    for (var i = 0; i < steps.length; i++) {
      var thisStep = steps[i];
      switch (thisStep.type) {
        case 'step':
        case 'disabled_step':
          renderStep(parent, thisStep);
          break;
        case 'concept':
          renderConcept(parent, thisStep);
          break;
      }
    }
  }

  function renderStep(parent, step) {
    var li = createElement('li', {class: "step"});
    var div = createElement('div', {class: step.status});
    renderSentence(div, step.sentence);
    if(step.comment) {
      renderUserCommentOnStep(step, div);    
    }
    
    li.appendChild(div);
    parent.appendChild(li);
 }

  function renderUserCommentOnStep(step, parent){
     var show = 'show comment';
     var hide = 'hide comment';
     var a = createElement('a', { href:'#', class: 'user_comment_link'}, show)  
     var stepCommentDiv = createElement('div', {class: 'step_comment'}, step.comment);
     $(stepCommentDiv).hide();
     $(a).on('click', function() {
        if($(this).text() == show ) {
          $(this).text(hide) 
        }
        else {
          $(this).text(show);
        }
        var element = $($(this).parent()).find('div')
        $(element).toggle();
        return false;
      });

      parent.appendChild(a);
      parent.appendChild(stepCommentDiv);
  }

  function renderConcept(parent, concept) {
    var li = createElement('li', {class: 'concept'});
    var div = createElement('div', {class: concept.status});
    renderSentence(div, concept.sentence);

    var ul = createElement('ul');
    renderSteps(ul, concept.steps);
    li.appendChild(div);
    li.appendChild(ul);

    parent.appendChild(li);
  }


  function createTableHeader(table, columns) {
    var thead = document.createElement('thead');
    var tr = document.createElement('tr');
    for (var i = 0; i < columns.length; i++) {
      var th = document.createElement('th');
      th.appendChild(document.createTextNode(columns[i]));
      tr.appendChild(th);
    }
    thead.appendChild(tr);
    table.appendChild(thead);
  }

  function addTableRow(tbody, columnValues, cssClass, attributeHash) {
    var tr = document.createElement('tr');
    tr.setAttribute('class', cssClass);
    for (var i = 0; i < columnValues.length; i++) {
      var td = document.createElement('td');
      td.appendChild(document.createTextNode(columnValues[i]));
      tr.appendChild(td);
    }
    if (attributeHash) {
      for (key in attributeHash) {
        tr.setAttribute(key, attributeHash[key]);
      }
    }
    tbody.appendChild(tr);
  }

  function renderSentence(parent, sentence) {
    for (var i = 0; i < sentence.fragments.length; i++) {
      var thisFragment = sentence.fragments[i];
      switch (thisFragment.type) {
        case 'fragment':
          parent.appendChild(document.createTextNode(thisFragment.text));
          break;
        case 'attributed_fragment':
          var cssClass = '';
          for (var j = 0; j < thisFragment.attributes.length; j++) {
            cssClass += thisFragment.attributes[j] + ' ';
          }
          parent.appendChild(createElement('span', {class: cssClass}, thisFragment.text));
          break;
        case 'parameter':
          parent.appendChild(createElement('span', {class: 'parameter'}, quotify(thisFragment.value)));
          break;
      }
    }
  }

  function quotify(value) {
    return '"' + value + '"';
  }

  beginProcessing();

};

(function() {

  var scenariosListContainer = null;
  var currentScenariosList = [];
  var selectedScenario = null;
  var filteredScenarioList = null;
  var thisSearchBox = null;

  function renderScenariosList(parent) {
    scenariosListContainer = parent;
    populateScenariosList(twistExecutionDetails.scenarios);
    hookEventHandlers();
  }


  function hookEventHandlers() {
    $('#scenarioContainer').on('click', '#previous,#next', function() {
      var action = $(this).data('action');
      if (currentScenariosList) {
        var index = currentScenariosList.indexOf(selectedScenario);
        if (index < 0){
          focusSearchBox();
          return;
        } 

        switch(action) {
          case 'previous':
            --index;
            break;
          case 'next':
            ++index;
            break;
        }

        if (index >= 0 && index < currentScenariosList.length) {
          markSelectedScenarioInList(index);
          selectedScenario = currentScenariosList[index];
          window.twist.getScenarioProcessor().process(selectedScenario);
        }

        focusSearchBox()
      }
    });
  }

  function markSelectedScenarioInList(index){
    var element = $('#scenarios').find('li').get(index);
    $($(element).siblings()).removeClass('selected');
    $(element).addClass('selected');
  }

  function populateScenariosList(scenarios) {
    currentScenariosList = scenarios;
    scenariosListContainer.innerHTML = '';

    var ul = createElement('ul', {id: 'scenarios'});
    for (var i = 0; i < scenarios.length; i++) {
      var thisScenario = scenarios[i];

      var li = createElement('li', {class: thisScenario.executionStatus});
      var link = createElement('a', {'data-scenario-index': twistExecutionDetails.scenarios.indexOf(thisScenario), href: '#', 'title': thisScenario.name});
      link.appendChild(createElement('span', {id: 'scenarioName'}, thisScenario.name));
      link.appendChild(createElement('span', {id: 'time'}, formatTime(thisScenario.executionTime)));
      
      li.appendChild(link);
      ul.appendChild(li);
    }

    scenariosListContainer.appendChild(ul);

    $(ul).on('click', 'a', function() {
      var scenarioIndex = $(this).data('scenario-index');
      setSelectedClassTo($(this).context.parentNode);
      var processor = window.twist.getScenarioProcessor();
      selectedScenario = twistExecutionDetails.scenarios[scenarioIndex];
      processor.process(selectedScenario);
      focusSearchBox();
      return false;

    });

    selectedScenario = null;

    $($(ul).find('a').get('0')).trigger('click');
  }

  function setSelectedClassTo(element){
    $(element).siblings().removeClass('selected');
    $(element).addClass('selected');
  }

  function renderSummary(parent) {
    var ul = createElement('ul');
    ul.appendChild(getSummaryItem('Executed', twistExecutionDetails.errorCount + 
          twistExecutionDetails.passCount + twistExecutionDetails.failureCount));
    ul.appendChild(getSummaryItem('Errors', twistExecutionDetails.errorCount, 'errored'));
    ul.appendChild(getSummaryItem('Failures', twistExecutionDetails.failureCount, 'failed'));
    ul.appendChild(getSummaryItem('Success Rate', twistExecutionDetails.successRate + '%'));
    ul.appendChild(getSummaryItem('Time', formatTime(twistExecutionDetails.executionTime)));
    parent.appendChild(ul);
  }

  function renderNotes(parent){
    var p = createElement('p', {class: 'identified_tags'}, 'Identified tags: ');
    var identifiedTags = createElement('span', {}, twistExecutionDetails.tags == "" ? 'N/A' : twistExecutionDetails.tags);
    p.appendChild(identifiedTags);
    parent.appendChild(p);
    parent.appendChild(createElement('p', {class: 'small'}, 'Failures are anticipated and checked with assertions while errors are unanticipated.'));
    
  }

  function getExecutedCount() {
    return twistExecutionDetails.errorCount + twistExecutionDetails.passCount + twistExecutionDetails.failureCount;
  }

  function getFailuresCount() {
    return twistExecutionDetails.errorCount + twistExecutionDetails.failureCount;
  }

  function getSummaryItem(label, value, cssClass) {
    if (!cssClass) {
      cssClass = '';
    }
    var li = createElement('li');
    var div1 = createElement('div', {class: 'key'}, label);
    var div2 = createElement('div', {class: 'value ' + cssClass }, value);
    li.appendChild(div1);
    li.appendChild(div2);

    return li;
  }

  function renderFilters(parent) {
    var countElement = createElement('span', {class: 'filterCount'}, "(" + getExecutedCount() + ")");
    var executed = createElement('a', {href: '#', 'data-filter': 'all'}, "Executed "); 
    executed.appendChild(countElement);
    var countElement = createElement('span', {class: 'filterCount'}, "(" + getFailuresCount() + ")");
    var failed = createElement('a', {href: '#', 'data-filter': 'failed'}, "Failed ");
    failed.appendChild(countElement);

    parent.appendChild(executed);
    parent.appendChild(failed);
    
    $(parent).on('click', 'a', function() {
      var filter = $(this).data('filter');
      $(this).siblings().removeClass('selected');
      $(this).addClass('selected');
      filterScenarioList(filter);
      var processor = window.twist.getScenarioProcessor();
      processor.clear();
      populateScenariosList(filteredScenarioList);

      if (filter == 'failed' && filteredScenarioList.length == 0) {
          processor.displayNoFailures();
      }

      clearAndFocusSearchBox();
      return false;
    });

    $($(parent).find('a').get('1')).trigger('click');
  }

  function filterScenarioList(filter) {
    var result = [];
    if (filter == 'all') {
      filteredScenarioList = twistExecutionDetails.scenarios;
      return;
    }

    for (var i = 0; i < twistExecutionDetails.scenarios.length; i++) {
      var thisScenario = twistExecutionDetails.scenarios[i];
      if (thisScenario.executionStatus != 'passed') {
        result.push(thisScenario);
      }
    }

   filteredScenarioList = result;
  }

  
  function initializeSearchBox(searchBox){
    if(searchBox){
      thisSearchBox = searchBox;
      clearAndFocusSearchBox();
      $(searchBox).keyup(function(){
        var text = $(this).val();
        search(text);
      })
    }
  }


  function search(text) {
    var results = [];
    var scenarios = filteredScenarioList;
    for (var i = 0; i < scenarios.length; i++) {
      if ((scenarios[i].name.toLowerCase().indexOf(text.toLowerCase())) !== -1) {
        results.push(scenarios[i]);
      }
    }
    
    populateScenariosList(results);
  }

  function clearAndFocusSearchBox(){
    if(thisSearchBox){
      $(thisSearchBox).val('');
      $(thisSearchBox).focus();
    }
  }

  function focusSearchBox(){
    if(thisSearchBox){
     $(thisSearchBox).focus(); 
    }
  }

  window.twist.renderScenariosList = renderScenariosList;
  window.twist.renderSummary = renderSummary;
  window.twist.renderNotes = renderNotes;
  window.twist.renderFilters = renderFilters;
  window.twist.initializeSearchBox = initializeSearchBox;
  window.twist.search = search;
  window.twist.filterScenarioList = filterScenarioList;
  
})();

function createElement(tagName, attributes, value){
  var element = document.createElement(tagName);

  if(attributes){
    for(key in attributes){
      element.setAttribute(key, attributes[key]);
    }

  }
  if(value !== undefined) {
    if (typeof(value) == 'object') {
      element.appendChild(value);
    }
    else {
      element.appendChild(document.createTextNode(value));
    }
  }

  return element;
}

function formatTime(timeInMs) {
  var sec = Math.floor(timeInMs / 1000);
  timeInMs %= 1000;

  var min = Math.floor(sec / 60);
  sec %= 60;

  var hour = Math.floor(min / 60);
  min %= 60;

  return convertTo2Digit(hour) + ":" + convertTo2Digit(min) + ":" + convertTo2Digit(sec);
}

function convertTo2Digit(value) {
  if (value < 10) {
    return "0" + value;
  }
  return value;
}


