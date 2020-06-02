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

const cube = document.getElementById("cube");
const nextButton = document.getElementById("next");
const currentClass = '';
const idx = 0;
const navMenuAnchorTags = document.querySelectorAll('.nav-menu a');
const interval;

// chane the side of cube on click
function changeSide() {
    const showClass = 'show-' + idx;
    if (currentClass) {
        cube.classList.remove(currentClass);
    }
    cube.classList.add(showClass);
    currentClass = showClass;
    idx = (idx + 1) % 6;
}
// set initial side
changeSide();

nextButton.addEventListener('click', changeSide);

// vertical scroll

for (const i = 0; i < navMenuAnchorTags.length; i++) {
    navMenuAnchorTags[i].addEventListener('click', function(event) {
        event.preventDefault();
        const targetSectionID = this.textContent.trim().toLowerCase();
        console.log(this.textContent);
        const targetSection = document.getElementById(targetSectionID);
        console.log(targetSectionID);
        interval = setInterval(function() {
            scrollVertically(targetSection);
        }, 20);
    });
}

function scrollVertically(targetSection) {
    const targetSectionCoordinates = targetSection.getBoundingClientRect();
    if (targetSectionCoordinates.top <= 0) {
        clearInterval(interval);
        return;
    }
    window.scrollBy(0, 50);
}