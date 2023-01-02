import { UserModel } from './UserModel'

export interface TeamResponseModel {
  id: number;

  name: string;

  description: string;
  
  manager: UserModel;

  users: UserModel[];
}
