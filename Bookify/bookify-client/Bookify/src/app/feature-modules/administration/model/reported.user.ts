export interface ReportedUser {
    id?:number,
    reason?:string,
    created?:number,
    reportedUser:{
        uid?:string,
        firstName?:string,
        lastName?:string,
        email?:string,
        blocked: boolean
    },
    createdBy:{
        uid?:string,
        firstName?:string,
        lastName?:string,
        email?:string
    }

}
