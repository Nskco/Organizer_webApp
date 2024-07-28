async function deleteContact(id) {
    Swal.fire({
        title: "Delete this contact?",
        showCancelButton: true,
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        confirmButtonColor: '#3085d6', // Blue color for confirm button
        cancelButtonColor: '#aaa', // Gray color for cancel button
        customClass: {
          confirmButton: 'swal2-confirm',
          cancelButton: 'swal2-cancel'
        }
    }).then(async (result) => {
        if (result.isConfirmed) {
            await fetch(`/user/contact/delete/${id}`, {
                method: 'DELETE'
            });
            Swal.fire("Deleted!", "", "success").then(() => {
                window.history.back();
            });
        }
    });
  }
  
  async function deleteContactTask(id) {
      Swal.fire({
          title: "Delete this Task?",
          showCancelButton: true,
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          confirmButtonColor: '#3085d6', // Blue color for confirm button
          cancelButtonColor: '#aaa', // Gray color for cancel button
          customClass: {
            confirmButton: 'swal2-confirm',
            cancelButton: 'swal2-cancel'
          }
      }).then(async (result) => {
          if (result.isConfirmed) {
              await fetch(`/user/contact/deleteContactTask/${id}`, {
                  method: 'DELETE'
              });
              Swal.fire("Deleted!", "", "success").then(() => {
                  location.reload();
              });
          }
      });
  }
  