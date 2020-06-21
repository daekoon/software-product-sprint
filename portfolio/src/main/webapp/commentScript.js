// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

document.addEventListener('DOMContentLoaded', displayComments(), false);

function displayComments() {
  fetch('/comments').then(response => response.json()).then(response => {
    const commentsContainer = document.getElementById('content');
    var html = '';
    if (!response.loggedIn) {
      html += '<p>You need to be logged in to see this page!</p>';
      html += '<p>Login <a href=\"' + response.loginURL + '\">here</a>.</p>' 
      commentsContainer.innerHTML = html;
      return;
    }
    
    html += '<p>Logout <a href=\"' + response.logoutURL + '\" >here</a>.</p>';
    html += '<h1>Posted Comments</h1><br/>';
    html += '<table id="commentsTable" width="80%">';
    html += '<tr>';
    html += '<th width="30%">Username</th>'
    html += '<th>Comment</th>'
    html += '</tr>';
    for (comment of response.comments) {
      html += '<tr>';
      html += '<td>';
      html += comment.username;
      html += '</td>';
      html += '<td>';
      html += comment.content;
      html += '</td>';
      html += '</tr>';
    }
    html += '</table>';
    html += '<br/><div id="comments-form">';
    html += '<form action="/comments" method="POST" id="commentform">';
    html += '<b>Name:</b> <input type="text" name="username"> <br/>';
    html += '<b>Comment:</b><br/>';
    html += '<textarea rows="4" cols="50" name="comment"></textarea>';
    html += '<br/>';
    html += '<input type="submit" />';
    html += '</form>';
    html += '</div>';
    commentsContainer.innerHTML = html;
  });
}

