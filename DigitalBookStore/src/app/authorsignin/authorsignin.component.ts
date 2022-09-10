import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-authorsignin',
  templateUrl: './authorsignin.component.html',
  styleUrls: ['./authorsignin.component.css']
})
export class AuthorsigninComponent implements OnInit {

  loginRequest ={
    authorname:"abc",
    password:"abcabcabc"

  }
  signInBlankResponse ={
    authorname:"",
    password:""
  }
  signInIvalidResponse:any;
  
  constructor(public userService: UserService,public router:Router) { }
  cancelsignin(){
    this.userService.digitalBooksContainerFlag=true;
    this.userService.slideShowFlag=true;
    this.router.navigate(["/"]);
  }
  authorSignIn(){
    const observable= this.userService.authorSignin(this.loginRequest);
    observable.subscribe((responseBody:any)=>{
      console.log("RB"+responseBody);
      this.signInBlankResponse.authorname=responseBody.authorname;
      this.signInBlankResponse.password=responseBody.password;
      
      
      //this.signInResponse=JSON.stringify(responseBody);
    },
    (error:any)=>{
      console.log("eR"+JSON.stringify(error.error));
      if(typeof error.error==='string'){
      this.signInIvalidResponse=error.error;
      }
      else{
        console.log(error.error.text);
       sessionStorage.setItem("authorName",this.loginRequest.authorname);
        sessionStorage.setItem('authorId',error.error.text.replace("Author Login Success",""));
        console.log(sessionStorage.getItem('authorId'));
        this.userService.authorsignupNavFlag=false;
        this.userService.createbooknavFlag=true;
        this.router.navigate(['/authorhome']);
      }
    });
  }
  ngOnInit(): void {
    this.userService.digitalBooksContainerFlag=false;
    this.userService.slideShowFlag=false;
  }

}
