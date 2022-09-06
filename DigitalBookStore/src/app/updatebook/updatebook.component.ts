import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-updatebook',
  templateUrl: './updatebook.component.html',
  styleUrls: ['./updatebook.component.css']
})
export class UpdatebookComponent implements OnInit {
  Books:any;
  constructor(public userService:UserService) { }
  
  ngOnInit(): void {
    const observable=this.userService.getbooksByAuthorID();
    observable.subscribe((responseBody:any)=>{
      console.log(JSON.stringify(responseBody));
      this.Books=JSON.parse(JSON.stringify(responseBody));
    },
    (error:any)=>{
      
    })
  }
  }

