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
    authorname:"",
    password:""

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
    this.signInBlankResponse.authorname="";
    this.signInBlankResponse.password="";
    this.signInIvalidResponse="";
    this.loginRequest.authorname=this.loginRequest.authorname.trim();
    let errorcount=0;
    if(this.loginRequest.authorname==="" || this.loginRequest.authorname.trim()===""){
      errorcount+=1;      
      this.loginRequest.authorname="";
      this.signInBlankResponse.authorname="Author Name Cannot be blank";
    }
    if(this.loginRequest.password==="" || this.loginRequest.password.trim()===""){
      errorcount+=1;
      this.loginRequest.password="";
    this.signInBlankResponse.password="Password Cannot be blank";
    }
    if(errorcount===0){
      const observable= this.userService.authorSignin(this.loginRequest);
      observable.subscribe((responseBody:any)=>{
        console.log("RB"+responseBody);
        this.signInBlankResponse.authorname=responseBody.authorname;
        this.signInBlankResponse.password=responseBody.password;
      },
      (error:any)=>{
        console.log("eR"+JSON.stringify(error.error));
        if(typeof error.error==='string'){
          this.signInIvalidResponse=error.error;
          this.loginRequest.authorname="";
          this.loginRequest.password="";
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
  }
  ngOnInit(): void {
    if(sessionStorage.getItem("authorId")===null){
      this.userService.digitalBooksContainerFlag=false;
      this.userService.slideShowFlag=false;
    }
    else{
      this.router.navigate(["/authorhome"]);
    }
  }
}
