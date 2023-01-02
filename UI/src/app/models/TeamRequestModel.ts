import {  UserModel } from "./UserModel";

export interface TeamRequestModel {
    name: string;

    description: string;
    
    manager: UserModel;
  }
