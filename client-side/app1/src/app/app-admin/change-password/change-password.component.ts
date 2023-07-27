import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { AdminServiceService } from '../admin-service.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  form: FormGroup;
  constructor(formBuilder: FormBuilder, private service: AdminServiceService)
  {
    this.form = formBuilder.group({
      email: new FormControl(),
      oldPassword: new FormControl(),
      password: new FormControl(),
      confirmPassword: new FormControl()  
    });
  }

  changePassword()
    {
      this.service.changePassword(this.form.value).subscribe(
        r1=>{
          console.log(r1);

        }
      )
    }
}
