import React from 'react'
import { Outlet } from 'react-router-dom'
import HeaderStaff from '../components/staffComponents/HeaderStaff.tsx'
export default function StaffLayout() {
  return (
    <div>
      <div><HeaderStaff/></div>
      <div><Outlet/></div>
    </div>
  )
}


