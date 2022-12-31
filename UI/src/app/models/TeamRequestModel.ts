import {  User } from "./User";

export interface TeamRequestModel {
    name: string;
    description: string;
    manager: User;
  }
  