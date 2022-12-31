import { User } from './User'

export interface TeamResponseModel {
  id: number;
  name: string;
  description: string;
  manager: User;
  users: User[];
}
