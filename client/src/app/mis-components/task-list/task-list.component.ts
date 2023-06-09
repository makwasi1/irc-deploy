import {Component, DoCheck, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TaskListService} from "../../services/task-list.service";
import {ProgramPartnersService} from "../../services/program-partners.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-tasklist',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  rows: any = [];
  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject<any>();

  constructor(private router: Router,
              private taskListService: TaskListService) {
  }

  count = 0;

  ngOnInit(): void {
    this.taskListService.getTaskList().subscribe(tasks => {
      this.rows = tasks;
      this.dtTrigger.next();
    }, error => console.log(error));

    this.dtOptions = {
      pagingType: "numbers",
      lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
      processing: true,
      responsive: true,
      dom: 'lfBrtip',
      buttons: []
    };
  }

}
