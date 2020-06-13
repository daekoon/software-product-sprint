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
  fetch('/comments').then(response => response.json()).then(comments => {
    const commentsContainer = document.getElementById('comments-container');
    var html = '<table id="commentsTable" width="80%">';
    html += '<tr>';
    html += '<th width="30%">Username</th>'
    html += '<th>Comment</th>'
    html += '</tr>';
    for (comment of comments) {
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
    console.log(html);
    commentsContainer.innerHTML = html;
  });
}

