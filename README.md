# Tasker v2

A simple CLI app used for managing tasks which uses Firebase Firestore.

## Usage:

`tasker.jar -get`
  Gets all completed and not completed tasks

`tasker.jar -get <label>`
  Gets all completed and not completed tasks that have the <label> label.
  
`tasker.jar -check <ID>`
  Makes the task with the ID of <ID> completed and checked

` tasker.jar -uncheck <ID>`
   Makes the task with the ID of <ID> uncompleted and unchecked
  
`tasker.jar <task_text>`
  Inserts a new task with the text of <task_text>. 
  
`tasker.jar <task_text> @label`
  Inserts a new task with the text of <task_text> and label of @label.
  
`tasker.jar`
  Prompts you to insert the task text.
