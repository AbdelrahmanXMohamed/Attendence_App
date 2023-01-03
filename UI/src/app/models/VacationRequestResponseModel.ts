import { VacationStatus } from "../utils/vacationStatus"
import { VacationType } from "../utils/vacationType"
import { UserModel } from "./UserModel";

export interface vacationRequestResponse{
    id:number;
    user:UserModel;
    type: VacationType

    status:VacationStatus

    startDate: Date

    endDate:Date

    comment:string

    numberOfDay:number
}