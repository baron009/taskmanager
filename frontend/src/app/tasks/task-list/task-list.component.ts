import { Component, OnInit } from '@angular/core';
import { TasksService, Task } from '../../api';

@Component({
  selector: 'app-task-list',
  template: `
    <div *ngFor="let task of tasks">
      <h3>{{ task.description }}</h3>
      <p>Status: {{ task.status }}</p>
    </div>
  `
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private tasksService: TasksService) {}

  ngOnInit() {
    this.tasksService.tasksGet().subscribe(
      tasks => this.tasks = tasks
    );
  }
}