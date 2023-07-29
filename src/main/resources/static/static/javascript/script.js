const search = () => {
    let query = $("#search_input").val();

    if (query === "") {
        $(".search_results").hide();
    } else {
        console.log(query);
        let url = `http://localhost:8080/search?query=${query}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Handle the fetched data
                $(".search_results").empty(); // Clear previous results

                // Loop through the data and create job cards
                data.forEach(job => {
                    let jobCard = `
            <div class="job-card">
              <div class="job-details">
                <h3 class="job-title">${job.title}</h3>
                <p class="company">${job.company}</p>
                <p class="location">${job.location}</p>
                <p class="job-mode">${job.jobMode}</p>
                <p class="experience-years">${job.experienceYears}</p>
              </div>
            </div>
            <div class="job-apply">
              <a class="btn btn-primary btn-lg" href="/viewjob/${job.id}">View Job</a>
            </div>
          `;

                    $(".search_results").append(jobCard); // Append job card to search results container
                });

                $(".search_results").show(); // Show the search results container
            })
            .catch(error => {
                console.error(error);
            });
    }
};
const searchLocation = () => {
    let location = $("#search_location_input").val();

    if (location === "") {
        $(".search_results_location").hide();
    } else {
        console.log(location);
        let url = `http://localhost:8080/searchlocation?location=${location}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Handle the fetched data
                $(".search_results_location").empty(); // Clear previous results

                // Loop through the data and create job cards
                data.forEach(job => {
                    let locationCard = `
            <div class="location-card">
              <div class="job-details">
                <h3 class="job-title">${job.title}</h3>
                <p class="company">${job.company}</p>
                <p class="location">${job.location}</p>
                <p class="job-mode">${job.jobMode}</p>
                <p class="experience-years">${job.experienceYears}</p>
              </div>
            </div>
            <div class="location-apply">
              <a class="btn btn-primary btn-lg" href="/viewjob/${job.id}">View Job</a>
            </div>
          `;

                    $(".search_results_location").append(locationCard); // Append job card to search results container
                });

                $(".search_results_location").show(); // Show the search results container
            })
            .catch(error => {
                console.error(error);
            });
    }
};
$("#job-mode-filter").change(function() {
    let selectedMode = $(this).val();

    if (selectedMode === "all") {
        // Handle the case when all job modes are selected
        // You can reset the search results or perform any other action
    } else {
        fetchJobsByMode(selectedMode);
    }
});

function fetchJobsByMode(mode) {
    let url = "/api/jobs-by-mode?mode=" + mode;

    $.ajax({
        url: url,
        type: "GET",
        success: function(data) {
            // Handle the fetched data
            $(".search_results").empty(); // Clear previous results

            // Loop through the data and create job cards
            data.forEach(job => {
                let jobCard = `
                    <div class="job-card">
                        <div class="job-details">
                            <h3 class="job-title">${job.title}</h3>
                            <p class="company">${job.company}</p>
                            <p class="location">${job.location}</p>
                            <p class="job-mode">${job.jobMode}</p>
                            <p class="experience-years">${job.experienceYears}</p>
                        </div>
                    </div>
                    <div class="job-apply">
                        <a class="btn btn-primary btn-lg" href="/viewjob/${job.id}">View Job</a>
                    </div>
                `;

                $(".search_results").append(jobCard); // Append job card to search results container
            });

            $(".search_results").show(); // Show the search results container
        },
        error: function(error) {
            console.error(error);
        }
    });
}
$(document).ready(function() {
    $('#remote-jobs-filter').change(function() {
        var isChecked = $(this).is(':checked');
        if (isChecked) {
            fetchRemoteJobs();
        } else {
            fetchAllJobs();
        }
    });

    function fetchRemoteJobs() {
        $.ajax({
            url: '/jobs?remote=true',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                // Handle the fetched data and update the UI
                displayJobs(data);
            },
            error: function(error) {
                console.error(error);
            }
        });
    }

    function fetchAllJobs() {
        $.ajax({
            url: '/jobs',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                // Handle the fetched data and update the UI
                displayJobs(data);
            },
            error: function(error) {
                console.error(error);
            }
        });
    }

    function displayJobs(jobs) {
        var $resultsContainer = $('#filtered-results');
        $resultsContainer.empty(); // Clear previous results

        jobs.forEach(function(job) {
            var jobCard = `
        <div class="job-card">
          <div class="job-details">
            <h3 class="job-title">${job.title}</h3>
            <p class="company">${job.company}</p>
            <p class="location">${job.location}</p>
            <p class="job-mode">${job.jobMode}</p>
            <p class="experience-years">${job.experienceYears}</p>
          </div>
        </div>
        <div class="job-apply">
          <a class="btn btn-primary btn-lg" href="/viewjob/${job.id}">View Job</a>
        </div>
      `;

            $resultsContainer.append(jobCard);
        });
    }
});
    function showSuccessMessage() {
    // Show the alert message
    var alertDiv = document.createElement('div');
    alertDiv.className = 'alert alert-success';
    alertDiv.role = 'alert';
    alertDiv.innerHTML = 'You have successfully applied for the job!';
    document.getElementById('liveAlertPlaceholder').appendChild(alertDiv);

    // Remove the alert message after 5 seconds
    setTimeout(function() {
    alertDiv.remove();
}, 5000);
};
