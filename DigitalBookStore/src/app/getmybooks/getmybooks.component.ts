import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-getmybooks',
  templateUrl: './getmybooks.component.html',
  styleUrls: ['./getmybooks.component.css']
})
export class GetmybooksComponent implements OnInit {

  constructor(public userService:UserService) { }

  ngOnInit(): void {
    this.userService.getMyBooks();
  }

}
