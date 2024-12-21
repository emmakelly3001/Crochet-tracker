// Elements
const addProjectBtn = document.getElementById('add-project-btn');
const projectFormModal = document.getElementById('project-form-modal');
const closeModalBtn = document.getElementById('close-modal-btn');
const projectForm = document.getElementById('project-form');
const projectList = document.getElementById('project-list');

// Open the project creation form (modal)
addProjectBtn.addEventListener('click', function() {
    projectFormModal.style.display = 'block';
});

// Close the project creation form (modal)
closeModalBtn.addEventListener('click', function() {
    projectFormModal.style.display = 'none';
});

// Handle form submission to create a new project
projectForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form from submitting normally

    // Get project details from the form
    const projectName = document.getElementById('project-name').value;
    const projectDescription = document.getElementById('project-description').value;

    // Create a new project item and append it to the project list
    const projectItem = document.createElement('div');
    projectItem.classList.add('project-item');
    projectItem.innerHTML = `
        <h3>${projectName}</h3>
        <p>${projectDescription}</p>
    `;
    projectList.appendChild(projectItem);

    // Close the modal and reset the form
    projectFormModal.style.display = 'none';
    projectForm.reset();
});
